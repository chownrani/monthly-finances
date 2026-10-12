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
import com.chownrani.finances.entities.VariableExpenseCategory;
import com.chownrani.finances.entities.VariableExpenseCompetence;
import com.chownrani.finances.repositories.CompetenceRepository;
import com.chownrani.finances.repositories.UserRepository;
import com.chownrani.finances.repositories.VariableExpenseCategoryRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
class VariableExpenseCompetenceServiceTest {

    @Autowired
    private VariableExpenseCompetenceService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompetenceRepository competenceRepository;

    @Autowired
    private VariableExpenseCategoryRepository variableExpenseCategoryRepository;

    @Test
    void crud() {
        User user = new User();
        user.setName("Rani");
        user.setEmail("variable-competence-" + UUID.randomUUID() + "@example.com");
        user = userRepository.save(user);

        Competence competence = new Competence();
        competence.setUser(user);
        competence.setYear(2026);
        competence.setMonth(10);
        competence = competenceRepository.save(competence);

        VariableExpenseCategory category = new VariableExpenseCategory();
        category.setUser(user);
        category.setName("Lazer");
        category = variableExpenseCategoryRepository.save(category);

        VariableExpenseCompetence entity = new VariableExpenseCompetence();
        entity.setCompetence(competence);
        entity.setCategory(category);
        entity.setAccumulatedAmount(new BigDecimal("150.00"));

        VariableExpenseCompetence created = service.create(entity);
        assertNotNull(created.getId());
        assertNotNull(created.getCreatedAt());

        assertTrue(service.findById(created.getId()).isPresent());
        assertFalse(service.findAll().isEmpty());

        created.setAccumulatedAmount(new BigDecimal("300.00"));
        VariableExpenseCompetence updated = service.update(created.getId(), created);
        assertEquals(0, new BigDecimal("300.00").compareTo(updated.getAccumulatedAmount()));

        service.delete(created.getId());
        assertTrue(service.findById(created.getId()).isEmpty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(created.getId()));
    }
}
