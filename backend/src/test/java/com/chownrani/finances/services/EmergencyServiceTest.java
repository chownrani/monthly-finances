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

import com.chownrani.finances.entities.Emergency;
import com.chownrani.finances.entities.User;
import com.chownrani.finances.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
class EmergencyServiceTest {

    @Autowired
    private EmergencyService service;

    @Autowired
    private UserRepository userRepository;

    @Test
    void crud() {
        User user = new User();
        user.setName("Rani");
        user.setEmail("emergency-" + UUID.randomUUID() + "@example.com");
        user = userRepository.save(user);

        Emergency entity = new Emergency();
        entity.setUser(user);
        entity.setGoal(new BigDecimal("10000.00"));

        Emergency created = service.create(entity);
        assertNotNull(created.getId());
        assertNotNull(created.getCreatedAt());

        assertTrue(service.findById(created.getId()).isPresent());
        assertFalse(service.findAll().isEmpty());

        created.setGoal(new BigDecimal("20000.00"));
        Emergency updated = service.update(created.getId(), created);
        assertEquals(0, new BigDecimal("20000.00").compareTo(updated.getGoal()));

        service.delete(created.getId());
        assertTrue(service.findById(created.getId()).isEmpty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(created.getId()));
    }
}
