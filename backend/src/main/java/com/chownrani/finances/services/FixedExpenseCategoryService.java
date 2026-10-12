package com.chownrani.finances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chownrani.finances.entities.FixedExpenseCategory;
import com.chownrani.finances.repositories.FixedExpenseCategoryRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FixedExpenseCategoryService {

    private final FixedExpenseCategoryRepository repository;

    public FixedExpenseCategory create(FixedExpenseCategory entity) {
        return repository.save(entity);
    }

    public Optional<FixedExpenseCategory> findById(Long id) {
        return repository.findById(id);
    }

    public List<FixedExpenseCategory> findAll() {
        return repository.findAll();
    }

    public FixedExpenseCategory update(Long id, FixedExpenseCategory entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("FixedExpenseCategory not found: " + id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("FixedExpenseCategory not found: " + id);
        }
        repository.deleteById(id);
    }
}
