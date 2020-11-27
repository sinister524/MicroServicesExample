package com.mytona.testtusk.CustomerService.mapper;

import com.mytona.testtusk.CustomerService.dto.OrderCreateDto;
import com.mytona.testtusk.CustomerService.pojo.OrderCreate;
import com.mytona.testtusk.CustomerService.pojo.Pair;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderCreateMapper {

    public OrderCreateDto pojoToDto (OrderCreate orderCreate) {
        return new OrderCreateDto(orderCreate.getCustomerName(), orderCreate.getCustomerEmail(), orderCreate.getCustomerPhone(),
                orderCreate.getCart().getProducts().entrySet().stream()
                    .map(productIntegerEntry -> new Pair(productIntegerEntry.getKey(), productIntegerEntry.getValue()))
                    .collect(Collectors.toList()));
    }
}
