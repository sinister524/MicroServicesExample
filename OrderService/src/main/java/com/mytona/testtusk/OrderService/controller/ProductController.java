package com.mytona.testtusk.OrderService.controller;

import com.mytona.testtusk.OrderService.entity.Product;
import com.mytona.testtusk.OrderService.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductRepository repository;

    @GetMapping("/get")
    public List<Product> getAllProducts (){
        return repository.findAll();
    }

    @GetMapping("/get/{id}")
    public Product getProduct (@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }


}
