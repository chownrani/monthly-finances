package com.chownrani.finances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chownrani.finances.entities.VariableExpenseCategory;

public interface VariableExpenseCategoryRepository extends JpaRepository<VariableExpenseCategory, Long> {
}
