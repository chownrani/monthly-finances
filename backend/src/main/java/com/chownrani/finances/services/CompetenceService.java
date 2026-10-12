package com.chownrani.finances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chownrani.finances.entities.Competence;
import com.chownrani.finances.repositories.CompetenceRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompetenceService {

    private final CompetenceRepository repository;

    public Competence create(Competence entity) {
        return repository.save(entity);
    }

    public Optional<Competence> findById(Long id) {
        return repository.findById(id);
    }

    public List<Competence> findAll() {
        return repository.findAll();
    }

    public Competence update(Long id, Competence entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Competence not found: " + id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Competence not found: " + id);
        }
        repository.deleteById(id);
    }
}
