package com.cloudacademy.bitcoin;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.CloseableHttpResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.io.InputStream;

public class ConverterSvc {

    private final String BITCOIN_CURRENTPRICE_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";
    private final HttpGet httpget = new HttpGet(BITCOIN_CURRENTPRICE_URL);

    private CloseableHttpClient httpclient;

    public ConverterSvc() {
        this.httpclient = HttpClients.createDefault();
    }

    public ConverterSvc(CloseableHttpClient httpclient) {
        this.httpclient = httpclient;
    }

    public enum Currency {
        USD, GBP, EUR
    }

    public double getExchangeRate(Currency currency) {
        double rate = 0;
        try (CloseableHttpResponse response = this.httpclient.execute(httpget)) {

            InputStream inputStream = response.getEntity().getContent();
            var json = new BufferedReader(new InputStreamReader(inputStream));

            @SuppressWarnings("deprecation")
            JsonObject jsonObject = JsonParser.parseReader(json).getAsJsonObject();
            String n = jsonObject.get("bpi").getAsJsonObject().get(currency.toString()).getAsJsonObject().get("rate")
                    .getAsString();
            NumberFormat nf = NumberFormat.getInstance();
            rate = nf.parse(n).doubleValue();

        } catch (Exception e) {
            rate = -1;
        }
        return rate;
    }

    public double convertBitcoins(Currency currency, double coins) {
        if (coins < 0) {
            throw new IllegalArgumentException("Coins must be greater than 0");
        }

        double currencyValue = 0;
        var exchangeRate = getExchangeRate(currency);

        if (exchangeRate >= 0) {
            currencyValue = exchangeRate * coins;
        } else {
            currencyValue = -1;
        }

        return currencyValue;
    }

}