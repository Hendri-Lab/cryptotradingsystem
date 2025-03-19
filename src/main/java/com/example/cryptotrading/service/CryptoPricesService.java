package com.example.cryptotrading.service;

import com.example.cryptotrading.models.CryptoPrices;

import java.util.List;

public interface CryptoPricesService {
    List<CryptoPrices> findAllCryptoPrices();

    void saveCryptoPrices(CryptoPrices cryptoPrices);

    CryptoPrices findByCryptoPair(String cryptoPair);
}
