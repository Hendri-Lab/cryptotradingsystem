package com.example.cryptotrading.dao;

import com.example.cryptotrading.models.CryptoPrices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoPricesRepository extends JpaRepository<CryptoPrices, Integer> {
}
