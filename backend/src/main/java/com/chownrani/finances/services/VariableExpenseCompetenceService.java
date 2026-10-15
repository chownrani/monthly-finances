package com.chownrani.finances.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.chownrani.finances.entities.VariableExpenseCompetence;
import com.chownrani.finances.repositories.VariableExpenseCompetenceRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VariableExpenseCompetenceService {

    private final VariableExpenseCompetenceRepository repository;

    public VariableExpenseCompetence create(VariableExpenseCompetence entity) {
        return repository.save(entity);
    }

    public Optional<VariableExpenseCompetence> findById(UUID id) {
        return repository.findById(id);
    }

    public List<VariableExpenseCompetence> findAll() {
        return repository.findAll();
    }

    public VariableExpenseCompetence update(UUID id, VariableExpenseCompetence entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("VariableExpenseCompetence not found: " + id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("VariableExpenseCompetence not found: " + id);
        }
        repository.deleteById(id);
    }
}
