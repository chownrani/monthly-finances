package com.chownrani.finances.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.chownrani.finances.entities.Account;
import com.chownrani.finances.entities.User;
import com.chownrani.finances.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
class AccountServiceTest {

    @Autowired
    private AccountService service;

    @Autowired
    private UserRepository userRepository;

    @Test
    void crud() {
        User user = new User();
        user.setName("Rani");
        user.setEmail("account-" + UUID.randomUUID() + "@example.com");
        user = userRepository.save(user);

        Account entity = new Account();
        entity.setUser(user);
        entity.setCurrentBalance(new BigDecimal("100.00"));

        Account created = service.create(entity);
        assertNotNull(created.getId());
        assertNotNull(created.getCreatedAt());

        assertTrue(service.findById(created.getId()).isPresent());
        assertFalse(service.findAll().isEmpty());

        created.setCurrentBalance(new BigDecimal("250.00"));
        Account updated = service.update(created.getId(), created);
        assertNotNull(updated.getId());

        service.delete(created.getId());
        assertTrue(service.findById(created.getId()).isEmpty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(created.getId()));
    }
}
