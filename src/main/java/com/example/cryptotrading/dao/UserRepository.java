package com.example.cryptotrading.dao;

import com.example.cryptotrading.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
