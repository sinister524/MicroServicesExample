package com.mytona.testtusk.OrderService.mapper;

import com.mytona.testtusk.OrderService.dto.OrderCreateDto;
import com.mytona.testtusk.OrderService.entity.Order;
import com.mytona.testtusk.OrderService.entity.OrderProduct;
import com.mytona.testtusk.OrderService.entity.OrderProductId;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderCreateMapper {
    public Order dtoToEntity (OrderCreateDto dto) {

        return new Order(dto.getCustomerName(), dto.getCustomerEmail(), dto.getCustomerPhone());

    }

}
