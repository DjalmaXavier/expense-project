package com.dx.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dx.expense.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
