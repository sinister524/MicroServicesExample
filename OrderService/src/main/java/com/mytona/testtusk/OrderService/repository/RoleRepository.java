package com.mytona.testtusk.OrderService.repository;

import com.mytona.testtusk.OrderService.entity.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    Boolean existsByName(String name);
}
