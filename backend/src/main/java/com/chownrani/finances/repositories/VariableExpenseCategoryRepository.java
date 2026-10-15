package com.chownrani.finances.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chownrani.finances.entities.VariableExpenseCategory;

public interface VariableExpenseCategoryRepository extends JpaRepository<VariableExpenseCategory, UUID> {
}
