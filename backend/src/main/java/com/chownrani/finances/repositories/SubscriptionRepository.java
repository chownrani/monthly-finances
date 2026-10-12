package com.chownrani.finances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chownrani.finances.entities.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
