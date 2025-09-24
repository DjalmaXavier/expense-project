package com.dx.expense.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dx.expense.entities.StatusCode;

public interface StatusCodeRepository extends JpaRepository<StatusCode, Long> {
    Optional<StatusCode> findByCode(String code);
}
