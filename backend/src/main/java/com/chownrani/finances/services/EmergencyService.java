package com.chownrani.finances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chownrani.finances.entities.Emergency;
import com.chownrani.finances.repositories.EmergencyRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmergencyService {

    private final EmergencyRepository repository;

    public Emergency create(Emergency entity) {
        return repository.save(entity);
    }

    public Optional<Emergency> findById(Long id) {
        return repository.findById(id);
    }

    public List<Emergency> findAll() {
        return repository.findAll();
    }

    public Emergency update(Long id, Emergency entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Emergency not found: " + id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Emergency not found: " + id);
        }
        repository.deleteById(id);
    }
}
