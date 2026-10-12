package com.chownrani.finances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chownrani.finances.entities.Investment;
import com.chownrani.finances.repositories.InvestmentRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvestmentService {

    private final InvestmentRepository repository;

    public Investment create(Investment entity) {
        return repository.save(entity);
    }

    public Optional<Investment> findById(Long id) {
        return repository.findById(id);
    }

    public List<Investment> findAll() {
        return repository.findAll();
    }

    public Investment update(Long id, Investment entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Investment not found: " + id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Investment not found: " + id);
        }
        repository.deleteById(id);
    }
}
