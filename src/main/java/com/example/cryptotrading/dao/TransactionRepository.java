package com.example.cryptotrading.dao;

import com.example.cryptotrading.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByUsername(String username);
}
