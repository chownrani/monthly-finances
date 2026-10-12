package com.chownrani.finances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chownrani.finances.entities.Investment;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
}
