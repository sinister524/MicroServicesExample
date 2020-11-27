package com.mytona.testtusk.CustomerService.service;

import com.mytona.testtusk.CustomerService.dto.OrderInfoDto;
import com.mytona.testtusk.CustomerService.mapper.OrderCreateMapper;
import com.mytona.testtusk.CustomerService.mapper.OrderInfoMapper;
import com.mytona.testtusk.CustomerService.pojo.OrderCreate;
import com.mytona.testtusk.CustomerService.pojo.OrderInfo;
import com.mytona.testtusk.CustomerService.pojo.Product;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Value("${url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    private final OrderCreateMapper createMapper;

    private final OrderInfoMapper infoMapper;

    public OrderService(OrderCreateMapper createMapper, OrderInfoMapper infoMapper) {
        this.createMapper = createMapper;
        this.infoMapper = infoMapper;
    }

    public List<OrderInfo> getAllOrderInfo () throws RestClientException {
        return Arrays.stream(Objects.requireNonNull(
                restTemplate.getForObject(url + "/orders/get", OrderInfoDto[].class)))
                    .map(infoMapper::dtoToPojo)
                    .collect(Collectors.toList());
    }

    public OrderInfo getOrderInfo(Long id) throws RestClientException{
        return infoMapper.dtoToPojo(Objects.requireNonNull(
                restTemplate.getForObject(url + "/orders/get/" + id, OrderInfoDto.class)));
    }

    public OrderInfo createOrder (OrderCreate orderCreate) throws RestClientException{
        return infoMapper.dtoToPojo(Objects.requireNonNull(
                restTemplate.postForObject(url + "/orders/add", createMapper.pojoToDto(orderCreate), OrderInfoDto.class)));
    }

    public OrderInfo editOrder (Long id, OrderCreate orderCreate) throws RestClientException{
        return infoMapper.dtoToPojo(Objects.requireNonNull(
                restTemplate.postForObject(url + "/orders/edit/" + id, createMapper.pojoToDto(orderCreate), OrderInfoDto.class)));
    }

    public OrderInfo payOrder (Long id) throws RestClientException{
        return infoMapper.dtoToPojo(Objects.requireNonNull(
                restTemplate.getForObject(url + "/orders/pay/" + id, OrderInfoDto.class)));
    }

    public OrderInfo deliverOrder (Long id) throws RestClientException{
        return infoMapper.dtoToPojo(Objects.requireNonNull(
                restTemplate.getForObject(url + "/orders/deliver/" + id, OrderInfoDto.class)));
    }

    public void deleteOrder (Long id) throws RestClientException{
        restTemplate.delete(url + "/orders/delete/" + id);
    }


    public OrderInfo confirmOrder(Long id) throws RestClientException{
        return infoMapper.dtoToPojo(Objects.requireNonNull(
                restTemplate.getForObject(url + "/orders/confirm/" + id, OrderInfoDto.class)));
    }

    public List<Product> getAllProducts() {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(url + "/products/get", Product[].class)));
    }

    public Product getProduct(Long id) {
        return restTemplate.getForObject(url + "/products/get/" + id, Product.class);
    }
}
