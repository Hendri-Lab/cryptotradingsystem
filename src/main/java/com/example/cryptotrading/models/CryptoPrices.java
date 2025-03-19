package com.example.cryptotrading.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "crypto_prices")
public class CryptoPrices {
    @Id
    @Column(name="crypto_pair")
    private String cryptoPair;

    @Column(name="price")
    BigDecimal price;

    @Column(name="created_at")
    LocalDateTime createdAt;

    public CryptoPrices() {
    }

    public CryptoPrices(String cryptoPair, BigDecimal price, LocalDateTime createdAt) {
        this.cryptoPair = cryptoPair;
        this.price = price;
        this.createdAt = createdAt;
    }

    public String getCryptoPair() {
        return cryptoPair;
    }

    public void setCryptoPair(String cryptoPair) {
        this.cryptoPair = cryptoPair;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "CryptoPrices{" +
                "cryptoPair='" + cryptoPair + '\'' +
                ", price=" + price +
                ", createdAt=" + createdAt +
                '}';
    }
}
