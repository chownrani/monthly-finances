package com.chownrani.finances.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chownrani.finances.entities.Emergency;

public interface EmergencyRepository extends JpaRepository<Emergency, UUID> {
}
