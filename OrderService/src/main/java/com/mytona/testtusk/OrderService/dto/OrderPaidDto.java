package com.mytona.testtusk.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaidDto {

    @NotNull
    private long id;

    @NotNull
    private boolean paid;
}
