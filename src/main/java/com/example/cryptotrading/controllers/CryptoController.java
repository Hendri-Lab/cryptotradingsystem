package com.example.cryptotrading.controllers;

import com.example.cryptotrading.models.CryptoPrices;
import com.example.cryptotrading.models.User;
import com.example.cryptotrading.models.Wallet;
import com.example.cryptotrading.service.CryptoPricesService;
import com.example.cryptotrading.service.TransactionService;
import com.example.cryptotrading.service.UserService;
import com.example.cryptotrading.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    final UserService userService;
    final WalletService walletService;
    final TransactionService transactionService;
    final CryptoPricesService cryptoPricesService;

    @Autowired
    public CryptoController(UserService userService, WalletService walletService, TransactionService transactionService, CryptoPricesService cryptoPricesService) {
        this.userService = userService;
        this.walletService = walletService;
        this.transactionService = transactionService;
        this.cryptoPricesService = cryptoPricesService;
    }

    @GetMapping("/balance/{username}")
    public Map<String, BigDecimal> getBalanceByUsername(@PathVariable String username) {
        List<Wallet> wallets = walletService.findAllWalletsByUsername(username);

        Map<String, BigDecimal> result = new HashMap<>();

        if (wallets.size() == 0) {
            result.put("USDT", new BigDecimal("0"));
            result.put("ETH", new BigDecimal("0"));
            result.put("BTC", new BigDecimal("0"));
        } else {
            for (Wallet wallet : wallets) {
                if (wallet.getCurrency().equals("USDT")) result.put("USDT", wallet.getBalance());
                else if (wallet.getCurrency().equals("ETH")) result.put("ETH", wallet.getBalance());
                else if (wallet.getCurrency().equals("BTC")) result.put("BTC", wallet.getBalance());
            }
        }
        return result;
    }

    @GetMapping("/latest-crypto-prices")
    public List<CryptoPrices> getLatestCryptoPrices() {
        return cryptoPricesService.findAllCryptoPrices();
    }

}
