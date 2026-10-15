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
import com.chownrani.finances.entities.FixedExpenseCategory;
import com.chownrani.finances.entities.Subscription;
import com.chownrani.finances.entities.SubscriptionCompetence;
import com.chownrani.finances.entities.User;
import com.chownrani.finances.repositories.CompetenceRepository;
import com.chownrani.finances.repositories.FixedExpenseCategoryRepository;
import com.chownrani.finances.repositories.SubscriptionRepository;
import com.chownrani.finances.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
class SubscriptionCompetenceServiceTest {

    @Autowired
    private SubscriptionCompetenceService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompetenceRepository competenceRepository;

    @Autowired
    private FixedExpenseCategoryRepository fixedExpenseCategoryRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    void crud() {
        User user = new User();
        user.setName("Rani");
        user.setEmail("subscription-competence-" + UUID.randomUUID() + "@example.com");
        user = userRepository.save(user);

        Competence competence = new Competence();
        competence.setUser(user);
        competence.setYear(2026);
        competence.setMonth(10);
        competence = competenceRepository.save(competence);

        FixedExpenseCategory category = new FixedExpenseCategory();
        category.setUser(user);
        category.setName("Assinaturas");
        category = fixedExpenseCategoryRepository.save(category);

        Subscription subscription = new Subscription();
        subscription.setFixedExpenseCategory(category);
        subscription.setName("Spotify");
        subscription.setDefaultValue(new BigDecimal("19.90"));
        subscription = subscriptionRepository.save(subscription);

        SubscriptionCompetence entity = new SubscriptionCompetence();
        entity.setCompetence(competence);
        entity.setSubscription(subscription);
        entity.setAmount(new BigDecimal("19.90"));

        SubscriptionCompetence created = service.create(entity);
        assertNotNull(created.getId());
        assertNotNull(created.getCreatedAt());

        assertTrue(service.findById(created.getId()).isPresent());
        assertFalse(service.findAll().isEmpty());

        created.setAmount(new BigDecimal("21.90"));
        SubscriptionCompetence updated = service.update(created.getId(), created);
        assertEquals(0, new BigDecimal("21.90").compareTo(updated.getAmount()));

        service.delete(created.getId());
        assertTrue(service.findById(created.getId()).isEmpty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(created.getId()));
    }
}
