package com.mytona.testtusk.OrderService.repository;

import com.mytona.testtusk.OrderService.entity.users.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByUsername(String username);
}
