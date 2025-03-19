package com.example.cryptotrading.dao;

import com.example.cryptotrading.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    List<Wallet> findAllByUsername(String username);
}
