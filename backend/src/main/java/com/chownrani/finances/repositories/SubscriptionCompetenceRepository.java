package com.chownrani.finances.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chownrani.finances.entities.SubscriptionCompetence;

public interface SubscriptionCompetenceRepository extends JpaRepository<SubscriptionCompetence, UUID> {
}
