package com.mytona.testtusk.CustomerService.pojo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthInfo {

    private String jwtToken;

    private Long id;

    private String name;

    private String email;

    private String phone;
}
