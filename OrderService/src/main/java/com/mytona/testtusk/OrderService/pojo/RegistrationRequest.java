package com.mytona.testtusk.OrderService.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;
}
