package com.example.cryptotrading.util;

public enum CryptoCurrency {
    USDT("USDT"),
    BTC("BTC"),
    ETH("ETH");

    private String val;

    CryptoCurrency(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
