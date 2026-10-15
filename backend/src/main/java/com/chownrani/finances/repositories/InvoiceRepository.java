package com.chownrani.finances.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chownrani.finances.entities.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
}
