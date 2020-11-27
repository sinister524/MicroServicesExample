package com.mytona.testtusk.OrderService.repository;

import com.mytona.testtusk.OrderService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
