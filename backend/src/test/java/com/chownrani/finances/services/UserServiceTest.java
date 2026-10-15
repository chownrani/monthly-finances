package com.chownrani.finances.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.chownrani.finances.entities.User;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    void crud() {
        User entity = new User();
        entity.setName("Rani");
        entity.setEmail("rani@example.com");

        User created = service.create(entity);
        assertNotNull(created.getId());
        assertNotNull(created.getCreatedAt());
        assertNotNull(created.getUpdatedAt());

        assertTrue(service.findById(created.getId()).isPresent());
        assertFalse(service.findAll().isEmpty());

        created.setName("Rani Updated");
        User updated = service.update(created.getId(), created);
        assertEquals("Rani Updated", updated.getName());

        service.delete(created.getId());
        assertTrue(service.findById(created.getId()).isEmpty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(created.getId()));
        assertThrows(EntityNotFoundException.class, () -> service.update(created.getId(), created));
    }
}
