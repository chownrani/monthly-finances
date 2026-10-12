package com.chownrani.finances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chownrani.finances.entities.VariableExpenseCategory;
import com.chownrani.finances.repositories.VariableExpenseCategoryRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VariableExpenseCategoryService {

    private final VariableExpenseCategoryRepository repository;

    public VariableExpenseCategory create(VariableExpenseCategory entity) {
        return repository.save(entity);
    }

    public Optional<VariableExpenseCategory> findById(Long id) {
        return repository.findById(id);
    }

    public List<VariableExpenseCategory> findAll() {
        return repository.findAll();
    }

    public VariableExpenseCategory update(Long id, VariableExpenseCategory entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("VariableExpenseCategory not found: " + id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("VariableExpenseCategory not found: " + id);
        }
        repository.deleteById(id);
    }
}
