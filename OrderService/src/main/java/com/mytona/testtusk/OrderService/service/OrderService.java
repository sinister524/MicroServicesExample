package com.mytona.testtusk.OrderService.service;

import com.mytona.testtusk.OrderService.dto.OrderDeliverDto;
import com.mytona.testtusk.OrderService.dto.OrderPaidDto;
import com.mytona.testtusk.OrderService.entity.Order;
import com.mytona.testtusk.OrderService.entity.OrderProduct;
import com.mytona.testtusk.OrderService.entity.OrderProductId;
import com.mytona.testtusk.OrderService.exception.OrderException;
import com.mytona.testtusk.OrderService.mapper.OrderDeliverMapper;
import com.mytona.testtusk.OrderService.mapper.OrderPaidMapper;
import com.mytona.testtusk.OrderService.pojo.Pair;
import com.mytona.testtusk.OrderService.repository.OrderProductRepository;
import com.mytona.testtusk.OrderService.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Value("${url.pay}")
    private String paymentUrl;

    @Value("${url.deliver}")
    private String deliverUrl;

    private OrderPaidMapper paidMapper;

    private OrderDeliverMapper deliverMapper;

    private OrderRepository orderRepository;

    private OrderProductRepository orderProductRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public OrderService(OrderPaidMapper paidMapper, OrderDeliverMapper deliverMapper, OrderRepository orderRepository, OrderProductRepository orderProductRepository) {
        this.paidMapper = paidMapper;
        this.deliverMapper = deliverMapper;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    public Order save (Order order, List<Pair> products) {
        Order orderFromDB = orderRepository.save(order);
        Set<OrderProduct> orderProductSet = products.stream()
                .map(pair -> new OrderProduct(new OrderProductId(orderFromDB.getId(), pair.getProduct().getId()), orderFromDB, pair.getProduct(), pair.getQuantity()))
                .collect(Collectors.toSet());
        String set = orderProductSet.toString();
        orderProductSet.forEach(orderProduct -> orderProductRepository.save(orderProduct));
        order.setOrderProducts(orderProductSet);
        orderFromDB.setOrderProducts(orderProductSet);
        return orderRepository.save(orderFromDB);
    }

    public Order get (Long id) throws OrderException{
        return orderRepository.findById(id).orElseThrow(()-> new OrderException(id, "not found"));
    }

    public List<Order> getAll () {
        return orderRepository.findAll();
    }

    public Order edit (Long id, Order order)  throws OrderException{
        Order orderFromDB = get(id);
        if (!orderFromDB.isConfirmed()){
            BeanUtils.copyProperties(order, orderFromDB, "id", "paid", "delivered");
        } else {
            throw new OrderException(id, "non editable");
        }
        return orderRepository.save(orderFromDB);
    }

    public Order pay(Long id) throws OrderException {
        Order orderFromDB = get(id);
        if (orderFromDB.isConfirmed()) {
            OrderPaidDto dto = paidMapper.entityToDto(orderFromDB);
            dto = restTemplate.postForObject(paymentUrl, dto, OrderPaidDto.class);
            orderFromDB.setPaid(Objects.requireNonNull(dto, "Payment failed").isPaid());
        } else {
            throw new OrderException(id, "non editable");
        }
        return orderRepository.save(orderFromDB);
    }

    public Order deliver(Long id)  throws OrderException {
        Order orderFromDB = get(id);
        if (orderFromDB.isPaid()) {
            OrderDeliverDto dto = deliverMapper.entityToDto(orderFromDB);
            dto = restTemplate.postForObject(deliverUrl, dto, OrderDeliverDto.class);
            orderFromDB.setDelivered(Objects.requireNonNull(dto, "Deliver failed").isDelivered());
        } else {
            throw new OrderException(id, "not paid");
        }
        return orderRepository.save(orderFromDB);
    }

    public void delete(Long id) throws OrderException {
        Order order = get(id);
        if (!order.isPaid() && !order.isDelivered()) {
            orderRepository.delete(order);
        } else {
            throw new OrderException(id, "cannot delete");
        }
    }

    public Order confirm(Long id) throws OrderException {
        Order orderFromDB = get(id);
        orderFromDB.setConfirmed(true);
        return orderRepository.save(orderFromDB);
    }
}
