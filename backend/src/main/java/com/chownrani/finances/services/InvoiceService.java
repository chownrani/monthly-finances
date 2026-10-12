package com.chownrani.finances.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chownrani.finances.entities.Invoice;
import com.chownrani.finances.repositories.InvoiceRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository repository;

    public Invoice create(Invoice entity) {
        return repository.save(entity);
    }

    public Optional<Invoice> findById(Long id) {
        return repository.findById(id);
    }

    public List<Invoice> findAll() {
        return repository.findAll();
    }

    public Invoice update(Long id, Invoice entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Invoice not found: " + id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Invoice not found: " + id);
        }
        repository.deleteById(id);
    }
}
