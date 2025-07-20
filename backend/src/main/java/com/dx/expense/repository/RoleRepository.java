package com.dx.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dx.expense.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
