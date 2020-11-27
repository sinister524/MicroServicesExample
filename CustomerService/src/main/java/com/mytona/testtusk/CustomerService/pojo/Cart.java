package com.mytona.testtusk.CustomerService.pojo;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class Cart {

    private Map<Product, Integer> products = new HashMap<>();

    public void addProduct (Product product) {
        if (products.containsKey(product)) {
            products.put(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
    }

    public void deleteProduct(Product product) {
        products.remove(product);
    }

    public void setProductQuantity (Product product, Integer quantity) {
        if (products.containsKey(product)) {
            products.put(product, quantity);
            if (products.get(product) < 1) {
                products.remove(product);
            }
        }
    }

    public List<Product> getAllProducts () {
        return products.keySet().stream()
                .sorted(Comparator.comparing(Product::getId))
                .collect(Collectors.toList());
    }

    public Integer getQuantity (Product product) {
        return products.getOrDefault(product, 0);
    }

    public boolean isEmpty(){
        return products.isEmpty();
    }
}
