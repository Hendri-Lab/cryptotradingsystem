package com.example.cryptotrading.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="wallet_id")
    private int walletId;

    @Column(name="user_id")
    String userId;

    @Column(name="username")
    String username;

    @Column(name="currency")
    String currency;

    @Column(name="balance")
    BigDecimal balance;

    public Wallet() {
    }

    public Wallet(String userId, String username, String currency, BigDecimal balance) {
        this.userId = userId;
        this.username = username;
        this.currency = currency;
        this.balance = balance;
    }

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "walletId=" + walletId +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", currency='" + currency + '\'' +
                ", balance=" + balance +
                '}';
    }
}
