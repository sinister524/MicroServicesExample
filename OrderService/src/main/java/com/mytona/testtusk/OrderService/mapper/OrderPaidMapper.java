package com.mytona.testtusk.OrderService.mapper;

import com.mytona.testtusk.OrderService.dto.OrderPaidDto;
import com.mytona.testtusk.OrderService.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderPaidMapper {


    public OrderPaidDto entityToDto (Order order) {
        return new OrderPaidDto(order.getId(), order.isPaid());
    }
}
