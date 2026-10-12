package com.chownrani.finances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chownrani.finances.entities.Emergency;

public interface EmergencyRepository extends JpaRepository<Emergency, Long> {
}
