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
import com.chownrani.finances.entities.Credit;
import com.chownrani.finances.entities.Invoice;
import com.chownrani.finances.entities.User;
import com.chownrani.finances.repositories.CompetenceRepository;
import com.chownrani.finances.repositories.CreditRepository;
import com.chownrani.finances.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
class InvoiceServiceTest {

    @Autowired
    private InvoiceService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompetenceRepository competenceRepository;

    @Autowired
    private CreditRepository creditRepository;

    @Test
    void crud() {
        User user = new User();
        user.setName("Rani");
        user.setEmail("invoice-" + UUID.randomUUID() + "@example.com");
        user = userRepository.save(user);

        Competence competence = new Competence();
        competence.setUser(user);
        competence.setYear(2026);
        competence.setMonth(10);
        competence = competenceRepository.save(competence);

        Credit credit = new Credit();
        credit.setUser(user);
        credit.setName("Nubank");
        credit.setClosingDay(5);
        credit.setDueDay(12);
        credit = creditRepository.save(credit);

        Invoice entity = new Invoice();
        entity.setCompetence(competence);
        entity.setCredit(credit);
        entity.setAmount(new BigDecimal("500.00"));

        Invoice created = service.create(entity);
        assertNotNull(created.getId());
        assertNotNull(created.getCreatedAt());

        assertTrue(service.findById(created.getId()).isPresent());
        assertFalse(service.findAll().isEmpty());

        created.setAmount(new BigDecimal("650.00"));
        Invoice updated = service.update(created.getId(), created);
        assertEquals(0, new BigDecimal("650.00").compareTo(updated.getAmount()));

        service.delete(created.getId());
        assertTrue(service.findById(created.getId()).isEmpty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(created.getId()));
    }
}
