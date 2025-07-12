package com.dx.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dx.expense.entities.Expenses;

public interface ExpensesRepository extends JpaRepository<Expenses, Long> {

}
