package com.chownrani.finances.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chownrani.finances.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
