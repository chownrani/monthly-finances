package com.chownrani.finances.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chownrani.finances.entities.Debit;

public interface DebitRepository extends JpaRepository<Debit, UUID> {
}
