package com.mytona.testtusk.PaymentService.service;

import com.mytona.testtusk.PaymentService.pojo.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {

    public Order makePayment(Order order) {
        order.setPaid(true);
        return order;
    }
}
