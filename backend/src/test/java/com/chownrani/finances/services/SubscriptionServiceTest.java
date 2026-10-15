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

import com.chownrani.finances.entities.FixedExpenseCategory;
import com.chownrani.finances.entities.Subscription;
import com.chownrani.finances.entities.User;
import com.chownrani.finances.repositories.FixedExpenseCategoryRepository;
import com.chownrani.finances.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
class SubscriptionServiceTest {

    @Autowired
    private SubscriptionService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FixedExpenseCategoryRepository fixedExpenseCategoryRepository;

    @Test
    void crud() {
        User user = new User();
        user.setName("Rani");
        user.setEmail("subscription-" + UUID.randomUUID() + "@example.com");
        user = userRepository.save(user);

        FixedExpenseCategory category = new FixedExpenseCategory();
        category.setUser(user);
        category.setName("Assinaturas");
        category = fixedExpenseCategoryRepository.save(category);

        Subscription entity = new Subscription();
        entity.setFixedExpenseCategory(category);
        entity.setName("Spotify");
        entity.setDefaultValue(new BigDecimal("19.90"));

        Subscription created = service.create(entity);
        assertNotNull(created.getId());
        assertNotNull(created.getCreatedAt());

        assertTrue(service.findById(created.getId()).isPresent());
        assertFalse(service.findAll().isEmpty());

        created.setName("Spotify Premium");
        Subscription updated = service.update(created.getId(), created);
        assertEquals("Spotify Premium", updated.getName());

        service.delete(created.getId());
        assertTrue(service.findById(created.getId()).isEmpty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(created.getId()));
    }
}
