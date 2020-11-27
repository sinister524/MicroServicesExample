package com.mytona.testtusk.OrderService.dto;

import com.mytona.testtusk.OrderService.pojo.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderInfoDto {
    private Long id;

    private String customerName;

    private String customerEmail;

    private String customerPhone;

    private boolean confirmed;

    private boolean paid;

    private boolean delivered;

    private List<Pair> products;
}

