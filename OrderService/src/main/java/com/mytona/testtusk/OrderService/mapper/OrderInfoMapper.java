package com.mytona.testtusk.OrderService.mapper;

import com.mytona.testtusk.OrderService.dto.OrderInfoDto;
import com.mytona.testtusk.OrderService.entity.Order;
import com.mytona.testtusk.OrderService.pojo.Pair;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderInfoMapper {

    public OrderInfoDto entityToDto (Order order) {

        return new OrderInfoDto(order.getId(), order.getCustomerName(), order.getCustomerEmail(),
                order.getCustomerPhone(), order.isConfirmed(), order.isPaid(), order.isDelivered(),
                order.getOrderProducts().stream()
                        .map(orderProduct -> new Pair(orderProduct.getProduct(), orderProduct.getQuantity()))
                        .collect(Collectors.toList()));
    }
}
