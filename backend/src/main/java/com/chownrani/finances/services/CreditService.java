package com.chownrani.finances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chownrani.finances.entities.Credit;
import com.chownrani.finances.repositories.CreditRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository repository;

    public Credit create(Credit entity) {
        return repository.save(entity);
    }

    public Optional<Credit> findById(Long id) {
        return repository.findById(id);
    }

    public List<Credit> findAll() {
        return repository.findAll();
    }

    public Credit update(Long id, Credit entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Credit not found: " + id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Credit not found: " + id);
        }
        repository.deleteById(id);
    }
}
