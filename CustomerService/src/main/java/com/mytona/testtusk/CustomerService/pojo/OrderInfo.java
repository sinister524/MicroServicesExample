package com.mytona.testtusk.CustomerService.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
    private Long id;

    private String customerName;

    private String customerEmail;

    private String customerPhone;

    private boolean confirmed;

    private boolean paid;

    private boolean delivered;

    private Cart cart;
}
