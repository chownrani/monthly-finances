package com.chownrani.finances.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.chownrani.finances.entities.Debit;
import com.chownrani.finances.repositories.DebitRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DebitService {

    private final DebitRepository repository;

    public Debit create(Debit entity) {
        return repository.save(entity);
    }

    public Optional<Debit> findById(UUID id) {
        return repository.findById(id);
    }

    public List<Debit> findAll() {
        return repository.findAll();
    }

    public Debit update(UUID id, Debit entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Debit not found: " + id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Debit not found: " + id);
        }
        repository.deleteById(id);
    }
}
