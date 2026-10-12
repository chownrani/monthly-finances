package com.chownrani.finances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chownrani.finances.entities.Credit;

public interface CreditRepository extends JpaRepository<Credit, Long> {
}
