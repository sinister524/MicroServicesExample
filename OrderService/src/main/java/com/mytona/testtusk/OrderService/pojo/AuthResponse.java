package com.mytona.testtusk.OrderService.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String jwtToken;

    private Long id;

    private String name;

    private String email;

    private String phone;
}
