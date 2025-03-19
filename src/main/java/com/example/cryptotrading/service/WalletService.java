package com.example.cryptotrading.service;

import com.example.cryptotrading.models.Wallet;

import java.util.List;

public interface WalletService {
    List<Wallet> findAllWalletsByUsername(String username);

    Wallet findByUsernameAndCurrency(String username, String currency);

    void saveWallet(Wallet wallet);
}
