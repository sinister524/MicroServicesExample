package com.mytona.testtusk.CustomerService.pojo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {


    private String username;

    private String email;

    private String phone;

    private String password;

    private String firstName;

    private String lastName;
}
