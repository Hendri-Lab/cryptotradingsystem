package com.example.cryptotrading.scheduler;

import com.example.cryptotrading.models.CryptoPrices;
import com.example.cryptotrading.service.CryptoPricesService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@EnableScheduling
public class getLatestPriceTask {
    private static final Logger logger = LoggerFactory.getLogger(getLatestPriceTask.class);

    final CryptoPricesService cryptoPricesService;

    @Autowired
    public getLatestPriceTask(CryptoPricesService cryptoPricesService) {
        this.cryptoPricesService = cryptoPricesService;
    }

    @Scheduled(fixedRate = 10000) // Runs every 10 seconds
    public void priceRetrievalTask() {
//        store the best pricing for customer
//        Bid Price use for SELL order, Ask Price use for BUY order
//        For sell orders(bid), store the highest bid price
//        For buy orders(ask), store the lowest ask price
        Map<String, BigDecimal> cryptoPricesBid = new HashMap<>();
        Map<String, BigDecimal> cryptoPricesAsk = new HashMap<>();

        cryptoPricesBid.put("ETHUSDT", null);
        cryptoPricesBid.put("BTCUSDT", null);

        cryptoPricesAsk.put("ETHUSDT", null);
        cryptoPricesAsk.put("BTCUSDT", null);

        retrievePrice("https://api.binance.com/api/v3/ticker/bookTicker", cryptoPricesBid, cryptoPricesAsk, false);
        retrievePrice("https://api.huobi.pro/market/tickers", cryptoPricesBid, cryptoPricesAsk, true);

        BigDecimal cryptoPricesBidBdEth = cryptoPricesBid.get("ETHUSDT");
        BigDecimal cryptoPricesBidBdBtc = cryptoPricesBid.get("BTCUSDT");

        BigDecimal cryptoPricesAskBdEth = cryptoPricesAsk.get("ETHUSDT");
        BigDecimal cryptoPricesAskBdBtc = cryptoPricesAsk.get("BTCUSDT");

        if (!Objects.isNull(cryptoPricesBidBdEth)) {
            CryptoPrices cryptoPrices = new CryptoPrices("ETHUSDT-BID", cryptoPricesBidBdEth, LocalDateTime.now());
            cryptoPricesService.saveCryptoPrices(cryptoPrices);
        }
        if (!Objects.isNull(cryptoPricesBidBdBtc)) {
            CryptoPrices cryptoPrices = new CryptoPrices("BTCUSDT-BID", cryptoPricesBidBdBtc, LocalDateTime.now());
            cryptoPricesService.saveCryptoPrices(cryptoPrices);
        }

        if (!Objects.isNull(cryptoPricesAskBdEth)) {
            CryptoPrices cryptoPrices = new CryptoPrices("ETHUSDT-ASK", cryptoPricesAskBdEth, LocalDateTime.now());
            cryptoPricesService.saveCryptoPrices(cryptoPrices);
        }
        if (!Objects.isNull(cryptoPricesAskBdBtc)) {
            CryptoPrices cryptoPrices = new CryptoPrices("BTCUSDT-ASK", cryptoPricesAskBdBtc, LocalDateTime.now());
            cryptoPricesService.saveCryptoPrices(cryptoPrices);
        }
    }


    private void retrievePrice(String url, Map<String, BigDecimal> cryptoPricesBid, Map<String, BigDecimal> cryptoPricesAsk, boolean useData) {
        OkHttpClient client = new OkHttpClient();
        Request binanceRequest = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(binanceRequest).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response " + response);
            }
            String data = response.body().string();

            Gson gson = new Gson();
            JsonArray jsonArray;

            if (!useData) jsonArray = gson.fromJson(data, JsonArray.class);
            else {
                JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
                jsonArray = jsonObject.get("data").getAsJsonArray();
            }

            int found = 0;
            for (JsonElement el : jsonArray) {
                if (found == 2) break;
                JsonObject jsonObj = el.getAsJsonObject();
                String symbol = jsonObj.get("symbol").getAsString();
                if (symbol.equalsIgnoreCase("ETHUSDT")) {
//                    bid price starts
                    String bidPriceStr = getPriceStr(useData, jsonObj, "bidPrice", "bid");
                    BigDecimal bidPrice = new BigDecimal(bidPriceStr);
                    bidPriceCalculation(cryptoPricesBid, bidPrice, "ETHUSDT");
//                    bid price ends

//                    ask price
                    String askPriceStr  = getPriceStr(useData, jsonObj, "askPrice", "ask");
                    BigDecimal askPrice = new BigDecimal(askPriceStr);
                    askPriceCalculation(cryptoPricesAsk, askPrice, "ETHUSDT");
//                    ask price ends

                    found++;
                }
                else if (symbol.equalsIgnoreCase("BTCUSDT")) {
//                    bid price
                    String bidPriceStr = getPriceStr(useData, jsonObj, "bidPrice", "bid");
                    BigDecimal bidPrice = new BigDecimal(bidPriceStr);
                    bidPriceCalculation(cryptoPricesBid, bidPrice, "BTCUSDT");
//                    bid price ends

//                    ask price
                    String askPriceStr  = getPriceStr(useData, jsonObj, "askPrice", "ask");
                    BigDecimal askPrice = new BigDecimal(askPriceStr);
                    askPriceCalculation(cryptoPricesAsk, askPrice, "BTCUSDT");
//                    ask price ends

                    found++;
                }
            }


        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage(), e);
        }
    }

    private void bidPriceCalculation(Map<String, BigDecimal> cryptoPricesBid, BigDecimal bidPrice, String cryptoPair) {
        if (Objects.isNull(cryptoPricesBid.get(cryptoPair))) cryptoPricesBid.put(cryptoPair, bidPrice);
        else {
            BigDecimal prevPrice = cryptoPricesBid.get(cryptoPair);
            BigDecimal maxPrice = bidPrice.compareTo(prevPrice) > 0 ? bidPrice : prevPrice;
            cryptoPricesBid.put(cryptoPair, maxPrice);
        }
    }

    private void askPriceCalculation(Map<String, BigDecimal> cryptoPricesAsk, BigDecimal askPrice, String cryptoPair) {
        if (Objects.isNull(cryptoPricesAsk.get(cryptoPair))) cryptoPricesAsk.put(cryptoPair, askPrice);
        else {
            BigDecimal prevPrice = cryptoPricesAsk.get(cryptoPair);
            BigDecimal minPrice = askPrice.compareTo(prevPrice) < 0 ? askPrice : prevPrice;
            cryptoPricesAsk.put(cryptoPair, minPrice);
        }
    }

    private String getPriceStr(boolean useData, JsonObject jsonObj, String priceStr, String priceStrAlt) {
        if (!useData) return jsonObj.get(priceStr).getAsString();
        else return jsonObj.get(priceStrAlt).getAsString();
    }

}
