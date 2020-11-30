package com.mytona.testtusk.OrderService.mapper;

import com.mytona.testtusk.OrderService.dto.OrderCreateDto;
import com.mytona.testtusk.OrderService.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderCreateMapper {
    public Order dtoToEntity (OrderCreateDto dto) {

        return new Order(dto.getCustomerName(), dto.getCustomerEmail(), dto.getCustomerPhone());

    }

}
