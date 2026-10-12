package com.chownrani.finances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chownrani.finances.entities.FixedExpenseCategory;

public interface FixedExpenseCategoryRepository extends JpaRepository<FixedExpenseCategory, Long> {
}
