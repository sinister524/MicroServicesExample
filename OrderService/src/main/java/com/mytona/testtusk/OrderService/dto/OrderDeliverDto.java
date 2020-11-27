package com.mytona.testtusk.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDeliverDto {

    @NotNull
    private long id;

    @NotNull
    private boolean delivered;
}
