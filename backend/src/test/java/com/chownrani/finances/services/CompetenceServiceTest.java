package com.chownrani.finances.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.chownrani.finances.entities.Competence;
import com.chownrani.finances.entities.User;
import com.chownrani.finances.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
class CompetenceServiceTest {

    @Autowired
    private CompetenceService service;

    @Autowired
    private UserRepository userRepository;

    @Test
    void crud() {
        User user = new User();
        user.setName("Rani");
        user.setEmail("competence-" + UUID.randomUUID() + "@example.com");
        user = userRepository.save(user);

        Competence entity = new Competence();
        entity.setUser(user);
        entity.setYear(2026);
        entity.setMonth(10);

        Competence created = service.create(entity);
        assertNotNull(created.getId());
        assertNotNull(created.getCreatedAt());

        assertTrue(service.findById(created.getId()).isPresent());
        assertFalse(service.findAll().isEmpty());

        created.setIncome(new BigDecimal("5000.00"));
        Competence updated = service.update(created.getId(), created);
        assertEquals(0, new BigDecimal("5000.00").compareTo(updated.getIncome()));

        service.delete(created.getId());
        assertTrue(service.findById(created.getId()).isEmpty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(created.getId()));
    }
}
