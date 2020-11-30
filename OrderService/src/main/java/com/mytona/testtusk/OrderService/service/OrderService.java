package com.mytona.testtusk.OrderService.service;

import com.mytona.testtusk.OrderService.dto.OrderDeliverDto;
import com.mytona.testtusk.OrderService.dto.OrderPaidDto;
import com.mytona.testtusk.OrderService.entity.Order;
import com.mytona.testtusk.OrderService.entity.OrderProduct;
import com.mytona.testtusk.OrderService.entity.OrderProductId;
import com.mytona.testtusk.OrderService.entity.users.Account;
import com.mytona.testtusk.OrderService.exception.BadRequestException;
import com.mytona.testtusk.OrderService.exception.ResourceNotFoundException;
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

    private final OrderPaidMapper paidMapper;

    private final OrderDeliverMapper deliverMapper;

    private final OrderRepository orderRepository;

    private final OrderProductRepository orderProductRepository;

    private final AccountService accountService;

    private final RestTemplate restTemplate = new RestTemplate();

    public OrderService(OrderPaidMapper paidMapper, OrderDeliverMapper deliverMapper, OrderRepository orderRepository,
                        OrderProductRepository orderProductRepository, AccountService accountService) {
        this.paidMapper = paidMapper;
        this.deliverMapper = deliverMapper;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.accountService = accountService;
    }

    public Order save (String username, Order order, List<Pair> products) {
        Account account = accountService.findByLogin(username);
        order.setAccount(account);
        Order orderFromDB = orderRepository.save(order);
        Set<OrderProduct> orderProductSet = products.stream()
                .map(pair -> new OrderProduct(new OrderProductId(orderFromDB.getId(), pair.getProduct().getId()),
                        orderFromDB, pair.getProduct(), pair.getQuantity()))
                .collect(Collectors.toSet());
        orderProductSet.forEach(orderProductRepository::save);
        order.setOrderProducts(orderProductSet);
        orderFromDB.setOrderProducts(orderProductSet);
        return orderRepository.save(orderFromDB);
    }

    public Order get (Long id, String username) {
        return orderRepository.findByIdAndAccountId(id, accountService.findByLogin(username).getId())
                .orElseThrow(()-> new ResourceNotFoundException("Order with Id - "+ id + " not found"));
    }

    public List<Order> getAll (String username) {
        Account account = accountService.findByLogin(username);
        return orderRepository.findAllByAccountId(account.getId());
    }

    public Order edit (Long id, Order order, String username, List<Pair> products) {
        Order orderFromDB = get(id, username);
        if (!orderFromDB.isConfirmed()){
            BeanUtils.copyProperties(order, orderFromDB, "id", "paid", "delivered", "account");
        } else {
            throw new BadRequestException("Order with Id - " + id + " non editable");
        }
        return save(username, orderFromDB, products);
    }

    public Order pay(Long id, String username) {
        Order orderFromDB = get(id, username);
        if (orderFromDB.isConfirmed()) {
            OrderPaidDto dto = paidMapper.entityToDto(orderFromDB);
            dto = restTemplate.postForObject(paymentUrl, dto, OrderPaidDto.class);
            orderFromDB.setPaid(Objects.requireNonNull(dto, "Payment failed").isPaid());
        } else {
            throw new BadRequestException("Order with Id - " + id + " not confirmed");
        }
        return orderRepository.save(orderFromDB);
    }

    public Order deliver(Long id, String username) {
        Order orderFromDB = get(id, username);
        if (orderFromDB.isPaid()) {
            OrderDeliverDto dto = deliverMapper.entityToDto(orderFromDB);
            dto = restTemplate.postForObject(deliverUrl, dto, OrderDeliverDto.class);
            orderFromDB.setDelivered(Objects.requireNonNull(dto, "Deliver failed").isDelivered());
        } else {
            throw new BadRequestException("Order with Id - " + id + " not paid");
        }
        return orderRepository.save(orderFromDB);
    }

    public void delete(Long id, String username) {
        Order order = get(id, username);
        if (!order.isPaid() && !order.isDelivered()) {
            orderRepository.delete(order);
        } else {
            throw new BadRequestException("Order with Id - " + id + " cannot delete");
        }
    }

    public Order confirm(Long id, String username) {
        Order orderFromDB = get(id, username);
        orderFromDB.setConfirmed(true);
        return orderRepository.save(orderFromDB);
    }
}
