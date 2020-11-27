package com.mytona.testtusk.OrderService.mapper;

import com.mytona.testtusk.OrderService.dto.OrderDeliverDto;
import com.mytona.testtusk.OrderService.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderDeliverMapper {

    public OrderDeliverDto entityToDto (Order order) {
        return new OrderDeliverDto(order.getId(), order.isDelivered());
    }
}
