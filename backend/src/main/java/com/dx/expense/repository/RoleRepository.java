package com.dx.expense.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dx.expense.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByDescription(String description);
}
