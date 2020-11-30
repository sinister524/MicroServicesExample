package com.mytona.testtusk.CustomerService.dto;

import com.mytona.testtusk.CustomerService.pojo.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
