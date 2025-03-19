package com.example.cryptotrading.service;

import com.example.cryptotrading.dao.UserRepository;
import com.example.cryptotrading.dao.WalletRepository;
import com.example.cryptotrading.models.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {
    final WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public List<Wallet> findAllWalletsByUsername(String username) {
        List<Wallet> wallets = walletRepository.findAllByUsername(username);
        return wallets;
    }
}
