package com.dx.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dx.expense.entities.ExpensesInstallments;

public interface ExpensesInstallmentsRepository extends JpaRepository<ExpensesInstallments, Long> {

}
