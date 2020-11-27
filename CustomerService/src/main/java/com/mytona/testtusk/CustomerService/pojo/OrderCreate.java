package com.mytona.testtusk.CustomerService.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCreate {

    private String customerName;

    private String customerEmail;

    private String customerPhone;

    private Cart cart;
}
