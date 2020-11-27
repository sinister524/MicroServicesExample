package com.mytona.testtusk.DeliveryService.service;


import com.mytona.testtusk.DeliveryService.pojo.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeliveryService {

    public Order makeDeliver(Order order) {
        order.setDelivered(true);
        return order;
    }
}
