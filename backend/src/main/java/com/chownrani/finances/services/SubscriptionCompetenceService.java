package com.chownrani.finances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chownrani.finances.entities.SubscriptionCompetence;
import com.chownrani.finances.repositories.SubscriptionCompetenceRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionCompetenceService {

    private final SubscriptionCompetenceRepository repository;

    public SubscriptionCompetence create(SubscriptionCompetence entity) {
        return repository.save(entity);
    }

    public Optional<SubscriptionCompetence> findById(Long id) {
        return repository.findById(id);
    }

    public List<SubscriptionCompetence> findAll() {
        return repository.findAll();
    }

    public SubscriptionCompetence update(Long id, SubscriptionCompetence entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("SubscriptionCompetence not found: " + id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("SubscriptionCompetence not found: " + id);
        }
        repository.deleteById(id);
    }
}
