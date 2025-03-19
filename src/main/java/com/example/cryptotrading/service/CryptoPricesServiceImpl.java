package com.example.cryptotrading.service;

import com.example.cryptotrading.dao.CryptoPricesRepository;
import com.example.cryptotrading.models.CryptoPrices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoPricesServiceImpl implements CryptoPricesService {
    final CryptoPricesRepository cryptoPricesRepository;

    @Autowired
    public CryptoPricesServiceImpl(CryptoPricesRepository cryptoPricesRepository) {
        this.cryptoPricesRepository = cryptoPricesRepository;
    }

    @Override
    public List<CryptoPrices> findAllCryptoPrices() {
        return cryptoPricesRepository.findAll();
    }

    @Override
    public void saveCryptoPrices(CryptoPrices cryptoPrices) {
        cryptoPricesRepository.save(cryptoPrices);
    }

    @Override
    public CryptoPrices findByCryptoPair(String cryptoPair) {
        return cryptoPricesRepository.findByCryptoPair(cryptoPair);
    }
}
