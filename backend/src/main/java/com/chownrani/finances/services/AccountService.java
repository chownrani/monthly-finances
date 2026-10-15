package com.chownrani.finances.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.chownrani.finances.entities.Account;
import com.chownrani.finances.repositories.AccountRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    public Account create(Account entity) {
        return repository.save(entity);
    }

    public Optional<Account> findById(UUID id) {
        return repository.findById(id);
    }

    public List<Account> findAll() {
        return repository.findAll();
    }

    public Account update(UUID id, Account entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Account not found: " + id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Account not found: " + id);
        }
        repository.deleteById(id);
    }
}
