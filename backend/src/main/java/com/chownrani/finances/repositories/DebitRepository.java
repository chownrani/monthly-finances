package com.chownrani.finances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chownrani.finances.entities.Debit;

public interface DebitRepository extends JpaRepository<Debit, Long> {
}
