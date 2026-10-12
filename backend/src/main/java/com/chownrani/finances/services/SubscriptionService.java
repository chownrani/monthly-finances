package com.chownrani.finances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chownrani.finances.entities.Subscription;
import com.chownrani.finances.repositories.SubscriptionRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository repository;

    public Subscription create(Subscription entity) {
        return repository.save(entity);
    }

    public Optional<Subscription> findById(Long id) {
        return repository.findById(id);
    }

    public List<Subscription> findAll() {
        return repository.findAll();
    }

    public Subscription update(Long id, Subscription entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Subscription not found: " + id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Subscription not found: " + id);
        }
        repository.deleteById(id);
    }
}
