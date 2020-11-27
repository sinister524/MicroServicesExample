package com.mytona.testtusk.OrderService.pojo;

import com.mytona.testtusk.OrderService.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pair {

    private Product product;

    private Integer quantity;

}
