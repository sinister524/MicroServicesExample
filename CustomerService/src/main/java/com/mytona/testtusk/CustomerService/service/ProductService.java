package com.mytona.testtusk.CustomerService.service;

import com.mytona.testtusk.CustomerService.pojo.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {
    @Value("${url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Product> getAllProducts() {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(url + "/products/get", Product[].class)));
    }

    public Product getProduct(Long id) {
        return restTemplate.getForObject(url + "/products/get/" + id, Product.class);
    }
}
