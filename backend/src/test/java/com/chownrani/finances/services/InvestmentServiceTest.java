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

import com.chownrani.finances.entities.Investment;
import com.chownrani.finances.entities.User;
import com.chownrani.finances.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
class InvestmentServiceTest {

    @Autowired
    private InvestmentService service;

    @Autowired
    private UserRepository userRepository;

    @Test
    void crud() {
        User user = new User();
        user.setName("Rani");
        user.setEmail("investment-" + UUID.randomUUID() + "@example.com");
        user = userRepository.save(user);

        Investment entity = new Investment();
        entity.setUser(user);
        entity.setIncomePercentage(new BigDecimal("0.10"));

        Investment created = service.create(entity);
        assertNotNull(created.getId());
        assertNotNull(created.getCreatedAt());

        assertTrue(service.findById(created.getId()).isPresent());
        assertFalse(service.findAll().isEmpty());

        created.setIncomePercentage(new BigDecimal("0.20"));
        Investment updated = service.update(created.getId(), created);
        assertEquals(0, new BigDecimal("0.20").compareTo(updated.getIncomePercentage()));

        service.delete(created.getId());
        assertTrue(service.findById(created.getId()).isEmpty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(created.getId()));
    }
}
