package com.example.cryptotrading.util;

public enum CryptoPair {
    ETHUSDT("ETHUSDT"),
    BTCUSDT("BTCUSDT"),
    ETHUSDTBID("ETHUSDT-BID"),
    BTCUSDTBID("BTCUSDT-BID"),
    ETHUSDTASK("ETHUSDT-ASK"),
    BTCUSDTASK("BTCUSDT-ASK");

    private String val;

    CryptoPair(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
