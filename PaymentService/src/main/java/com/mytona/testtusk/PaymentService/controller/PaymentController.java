package com.mytona.testtusk.PaymentService.controller;

import com.mytona.testtusk.PaymentService.pojo.Order;
import com.mytona.testtusk.PaymentService.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public Order makePayment(@RequestBody Order order) {
        return service.makePayment(order);
    }
}
