package com.mytona.testtusk.OrderService.dto;

import com.mytona.testtusk.OrderService.pojo.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDto {

    @NotNull
    private String customerName;

    @NotNull
    private String customerEmail;

    @NotNull
    private String customerPhone;

    @NotNull
    private List<Pair> products;

}
