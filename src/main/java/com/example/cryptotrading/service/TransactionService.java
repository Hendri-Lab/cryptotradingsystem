package com.example.cryptotrading.service;

import com.example.cryptotrading.models.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> findAllTransactionsByUser(String username);

}
