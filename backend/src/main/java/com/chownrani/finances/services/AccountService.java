package com.chownrani.finances.services;

import java.util.List;
import java.util.Optional;

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

    public Optional<Account> findById(Long id) {
        return repository.findById(id);
    }

    public List<Account> findAll() {
        return repository.findAll();
    }

    public Account update(Long id, Account entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Account not found: " + id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Account not found: " + id);
        }
        repository.deleteById(id);
    }
}
