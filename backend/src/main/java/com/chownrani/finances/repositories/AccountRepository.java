package com.chownrani.finances.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chownrani.finances.entities.Account;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
