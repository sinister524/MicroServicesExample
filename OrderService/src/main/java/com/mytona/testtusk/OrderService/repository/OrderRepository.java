package com.mytona.testtusk.OrderService.repository;

import com.mytona.testtusk.OrderService.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByAccountId(Long accountId);

    Optional<Order> findByIdAndAccountId(Long id, Long accountId);
}
