package com.example.cryptotrading.service;

import com.example.cryptotrading.dao.TransactionRepository;
import com.example.cryptotrading.models.CryptoPrices;
import com.example.cryptotrading.models.Transaction;
import com.example.cryptotrading.models.User;
import com.example.cryptotrading.models.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.example.cryptotrading.util.CryptoCurrency.*;
import static com.example.cryptotrading.util.CryptoPair.*;

@Service
public class TransactionServiceImpl implements TransactionService {
    final TransactionRepository transactionRepository;
    final UserService userService;
    final WalletService walletService;
    final CryptoPricesService cryptoPricesService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, UserService userService, WalletService walletService, CryptoPricesService cryptoPricesService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.walletService = walletService;
        this.cryptoPricesService = cryptoPricesService;
    }

    @Override
    public List<Transaction> findAllTransactionsByUser(String username) {
        return transactionRepository.findAllByUsername(username);
    }

    @Override
    @Transactional
    public String saveTransaction(Transaction transaction) {
        User user = userService.findUserByUsername(transaction.getUsername());
        if (Objects.isNull(user)) return "user not found";

//        Bid Price use for SELL order, Ask Price use for BUY order
        if (transaction.getTransactionType().equalsIgnoreCase("buy")) {
            Wallet wallet = walletService.findByUsernameAndCurrency(transaction.getUsername(), USDT.getVal());
            CryptoPrices cryptoPrices = null;
            String currency = null;
            if (transaction.getCryptoPair().equalsIgnoreCase(ETHUSDT.getVal())) {
                cryptoPrices = cryptoPricesService.findByCryptoPair(ETHUSDTASK.getVal());
                currency = ETH.getVal();
            }
            else if (transaction.getCryptoPair().equalsIgnoreCase(BTCUSDT.getVal())) {
                cryptoPrices = cryptoPricesService.findByCryptoPair(BTCUSDTASK.getVal());
                currency = BTC.getVal();
            }

            if (Objects.isNull(cryptoPrices)) return "crypto prices not found";
            BigDecimal price = cryptoPrices.getPrice();
            BigDecimal total = price.multiply(transaction.getAmount());

            if (total.compareTo(wallet.getBalance()) > 0) return "insufficient wallet balance";

            insertTransaction("buy", transaction, user.getUserId(), price, total);
            updateWallet(wallet, total, currency, transaction.getUsername(), transaction.getAmount());
        }
        else if (transaction.getTransactionType().equalsIgnoreCase("sell")) {
            CryptoPrices cryptoPrices = null;
            String currency = null;
            if (transaction.getCryptoPair().equalsIgnoreCase(ETHUSDT.getVal())) {
                cryptoPrices = cryptoPricesService.findByCryptoPair(ETHUSDTBID.getVal());
                currency = ETH.getVal();
            }
            else if (transaction.getCryptoPair().equalsIgnoreCase(BTCUSDT.getVal())) {
                cryptoPrices = cryptoPricesService.findByCryptoPair(BTCUSDTBID.getVal());
                currency = BTC.getVal();
            }
            Wallet wallet = walletService.findByUsernameAndCurrency(transaction.getUsername(), currency);

            if (Objects.isNull(cryptoPrices)) return "crypto prices not found";
            if (transaction.getAmount().compareTo(wallet.getBalance()) > 0) return "insufficient wallet balance";

            BigDecimal price = cryptoPrices.getPrice();
            BigDecimal total = price.multiply(transaction.getAmount());

            insertTransaction("sell", transaction, user.getUserId(), price, total);
            updateWallet(wallet, transaction.getAmount(), USDT.getVal(), transaction.getUsername(), total);
        } else return "error";

        return "success";
    }

    private void insertTransaction(String transactionType, Transaction transaction, int userId, BigDecimal price, BigDecimal total) {
        transaction.setCryptoPair(transaction.getCryptoPair().toUpperCase());
        transaction.setTransactionType(transactionType);
        transaction.setUserId(userId);
        transaction.setPrice(price);
        transaction.setTotal(total);
        transaction.setCreatedAt(LocalDateTime.now());

        transactionRepository.save(transaction);
    }

    private void updateWallet(Wallet wallet, BigDecimal subtractAmount, String walletToUpdateCurrency, String username, BigDecimal addAmount) {
        wallet.setBalance(wallet.getBalance().subtract(subtractAmount));
        walletService.saveWallet(wallet);

        Wallet walletToUpdate = walletService.findByUsernameAndCurrency(username, walletToUpdateCurrency);
        walletToUpdate.setBalance(walletToUpdate.getBalance().add(addAmount));
        walletService.saveWallet(walletToUpdate);
    }

}
