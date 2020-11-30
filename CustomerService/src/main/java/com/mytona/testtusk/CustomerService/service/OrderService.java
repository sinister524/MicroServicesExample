package com.mytona.testtusk.CustomerService.service;

import com.mytona.testtusk.CustomerService.dto.OrderCreateDto;
import com.mytona.testtusk.CustomerService.dto.OrderInfoDto;
import com.mytona.testtusk.CustomerService.mapper.OrderCreateMapper;
import com.mytona.testtusk.CustomerService.mapper.OrderInfoMapper;
import com.mytona.testtusk.CustomerService.pojo.OrderCreate;
import com.mytona.testtusk.CustomerService.pojo.OrderInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

    private final OrderInfoMapper infoMapper;

    private final OrderCreateMapper createMapper;

    public OrderService(OrderInfoMapper infoMapper, OrderCreateMapper createMapper) {
        this.infoMapper = infoMapper;
        this.createMapper = createMapper;
    }

    public List<OrderInfo> getAllOrderInfo (String token) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return Arrays.stream(Objects.requireNonNull(
                restTemplate.exchange(url + "/orders/get", HttpMethod.GET, httpEntity, OrderInfoDto[].class).getBody()))
                    .map(infoMapper::dtoToPojo)
                    .collect(Collectors.toList());
    }

    public OrderInfo getOrderInfo(Long id, String token) throws RestClientException{
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return infoMapper.dtoToPojo(Objects.requireNonNull(
                restTemplate.exchange(url + "/orders/get/" + id, HttpMethod.GET, httpEntity, OrderInfoDto.class).getBody()));
    }

    public OrderInfo createOrder (String token, OrderCreate orderCreate) throws RestClientException{
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<OrderCreateDto> httpEntity = new HttpEntity<>(createMapper.pojoToDto(orderCreate), headers);
        return infoMapper.dtoToPojo(Objects.requireNonNull(
                restTemplate.exchange(url + "/orders/add", HttpMethod.POST, httpEntity, OrderInfoDto.class).getBody()));
//        return infoMapper.dtoToPojo(Objects.requireNonNull(
//                restTemplate.postForObject(url + "/orders/add", createMapper.pojoToDto(orderCreate), OrderInfoDto.class)));
    }

    public OrderInfo editOrder (Long id, OrderCreate orderCreate, String token) throws RestClientException{
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<OrderCreateDto> httpEntity = new HttpEntity<>(createMapper.pojoToDto(orderCreate), headers);
        return infoMapper.dtoToPojo(Objects.requireNonNull(
                restTemplate.exchange(url + "/orders/edit/" + id, HttpMethod.POST, httpEntity, OrderInfoDto.class).getBody()));
//        return infoMapper.dtoToPojo(Objects.requireNonNull(
//                restTemplate.postForObject(url + "/orders/edit/" + id, createMapper.pojoToDto(orderCreate), OrderInfoDto.class)));
    }

    public OrderInfo payOrder (Long id, String token) throws RestClientException{
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return infoMapper.dtoToPojo(Objects.requireNonNull(
                restTemplate.exchange(url + "/orders/pay/" + id, HttpMethod.GET, httpEntity, OrderInfoDto.class).getBody()));
//        return infoMapper.dtoToPojo(Objects.requireNonNull(
//                restTemplate.getForObject(url + "/orders/pay/" + id, OrderInfoDto.class)));
    }

    public OrderInfo deliverOrder (Long id, String token) throws RestClientException{
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return infoMapper.dtoToPojo(Objects.requireNonNull(
                restTemplate.exchange(url + "/orders/deliver/" + id, HttpMethod.GET, httpEntity, OrderInfoDto.class).getBody()));
//        return infoMapper.dtoToPojo(Objects.requireNonNull(
//                restTemplate.getForObject(url + "/orders/deliver/" + id, OrderInfoDto.class)));
    }

    public void deleteOrder (Long id, String token) throws RestClientException{
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        restTemplate.exchange(url + "/orders/delete/" + id, HttpMethod.DELETE, httpEntity, Void.class);
//        restTemplate.delete(url + "/orders/delete/" + id);
    }


    public OrderInfo confirmOrder(Long id, String token) throws RestClientException{
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return infoMapper.dtoToPojo(Objects.requireNonNull(
                restTemplate.exchange(url + "/orders/confirm/" + id, HttpMethod.GET, httpEntity, OrderInfoDto.class).getBody()));
//        return infoMapper.dtoToPojo(Objects.requireNonNull(
//                restTemplate.getForObject(url + "/orders/confirm/" + id, OrderInfoDto.class)));
    }

}
