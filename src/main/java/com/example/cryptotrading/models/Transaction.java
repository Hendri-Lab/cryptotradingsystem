package com.example.cryptotrading.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transaction_id")
    private int transactionId;

    @JsonIgnore
    @Column(name="user_id")
    int userId;

    @Column(name="username")
    String username;

    @Column(name="transaction_type")
    String transactionType;

    @Column(name="crypto_pair")
    String cryptoPair;

    @Column(name="amount")
    BigDecimal amount;

    @Column(name="price")
    BigDecimal price;

    @Column(name="total")
    BigDecimal total;

    @Column(name="created_at")
    LocalDateTime createdAt;

    public Transaction() {
    }

    public Transaction(int userId, String username, String transactionType, String cryptoPair, BigDecimal amount, BigDecimal price, BigDecimal total, LocalDateTime createdAt) {
        this.userId = userId;
        this.username = username;
        this.transactionType = transactionType;
        this.cryptoPair = cryptoPair;
        this.amount = amount;
        this.price = price;
        this.total = total;
        this.createdAt = createdAt;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getCryptoPair() {
        return cryptoPair;
    }

    public void setCryptoPair(String cryptoPair) {
        this.cryptoPair = cryptoPair;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", cryptoPair='" + cryptoPair + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", total=" + total +
                ", createdAt=" + createdAt +
                '}';
    }
}
