package com.mytona.testtusk.DeliveryService.controller;

import com.mytona.testtusk.DeliveryService.pojo.Order;
import com.mytona.testtusk.DeliveryService.service.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DeliveryController {

    private final DeliveryService service;

    @PostMapping
    public Order makeDeliver(@RequestBody Order order) {
        return service.makeDeliver(order);
    }
}
