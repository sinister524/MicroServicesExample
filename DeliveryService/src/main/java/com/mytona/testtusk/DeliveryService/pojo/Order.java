package com.mytona.testtusk.DeliveryService.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {

    private Long id;

    private boolean delivered;
}
