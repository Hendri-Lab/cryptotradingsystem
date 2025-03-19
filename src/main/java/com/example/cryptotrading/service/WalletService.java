package com.example.cryptotrading.service;

import com.example.cryptotrading.models.Wallet;

import java.util.List;

public interface WalletService {
    List<Wallet> findAllWalletsByUsername(String username);
}
