package com.cloudacademy.bitcoin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.StatusLine;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import static org.mockito.Mockito.*;

import java.io.IOException;

public class ConverterSvcTest {

    private CloseableHttpClient client;
    private CloseableHttpResponse response;
    private HttpEntity entity;
    private StatusLine statusLine;
    private InputStream stream;

    @BeforeEach
    public void setUp() {
        client = mock(CloseableHttpClient.class);
        response = mock(CloseableHttpResponse.class);
        entity = mock(HttpEntity.class);
        statusLine = mock(StatusLine.class);
        stream = new ByteArrayInputStream("{\"time\": {\"updated\": \"Oct 15, 2020 22:55:00 UTC\",\"updatedISO\": \"2020-10-15T22:55:00+00:00\",\"updateduk\": \"Oct 15, 2020 at 23:55 BST\"},\"chartName\": \"Bitcoin\",\"bpi\": {\"USD\": {\"code\": \"USD\",\"symbol\": \"&#36;\",\"rate\": \"11,486.5341\",\"description\": \"United States Dollar\",\"rate_float\": 11486.5341},\"GBP\": {\"code\": \"GBP\",\"symbol\": \"&pound;\",\"rate\": \"8,900.8693\",\"description\": \"British Pound Sterling\",\"rate_float\": 8900.8693},\"EUR\": {\"code\": \"EUR\",\"symbol\": \"&euro;\",\"rate\": \"9,809.3278\",\"description\": \"Euro\",\"rate_float\": 9809.3278}}}".getBytes());

    }
    
    @Test
    public void getExchangeRate_USD_ReturnsUSDExchangeRate() throws IOException {
        // arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        // act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.getExchangeRate(ConverterSvc.Currency.USD);
        // assert
        double expected = 11486.5341;
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void getExchangeRate_GBP_ReturnsUSDExchangeRate() throws IOException {
        // arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        // act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.getExchangeRate(ConverterSvc.Currency.GBP);
        // assert
        double expected = 8900.8693;
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void getExchangeRate_EUR_ReturnsUSDExchangeRate() throws IOException {
       // arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        // act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.getExchangeRate(ConverterSvc.Currency.EUR);
        // assert
        double expected = 9809.3278;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getExchangeRate_ClosingResponseThrowsIOException_ReturnsCorrectErrorCode() throws IOException {
        // arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        doThrow(IOException.class).when(response).close();

        // act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.getExchangeRate(ConverterSvc.Currency.USD);
        // assert
        double expected = -1;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_1BitCoinToUSD_ReturnUSDollars() throws IOException {
        // arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        // act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.USD, 1);
        // assert
        double expected = 11486.5341;
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void convertBitcoins_2BitCoinToUSD_ReturnUSDollars() throws IOException {
        // arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        // act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.USD, 2);
        // assert
        double expected = 11486.5341 * 2;
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void convertBitcoins_1BitCoinToGBP_ReturnsGBP() throws IOException {
        // arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        // act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.GBP, 1);
        // assert
        double expected = 8900.8693;
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void convertBitcoins_2BitCoinToGBP_ReturnsGBP() throws IOException {
        // arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        // act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.GBP, 2);
        // assert
        double expected = 8900.8693 * 2;
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void convertBitcoins_1BitCoinToEUR_ReturnsEUR() throws IOException {
        // arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        // act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.EUR, 1);
        // assert
        double expected = 9809.3278;
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void convertBitcoins_2BitCoinToEUR_ReturnsEUR() throws IOException {
        // arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        // act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.EUR, 2);
        // assert
        double expected = 9809.3278 * 2;
        Assertions.assertEquals(expected, actual);

    }

     @Test
    public void convertBitcoins_0BitCoinToEUR_ReturnsZeroEURDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);
    
        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.EUR, 0);
    
        //assert
        double expected = 0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_NegativeBitCoinsToUSD_ThrowsException() throws IOException {
        // arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        // act
        ConverterSvc converterSvc = new ConverterSvc(client);
      
        // assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> converterSvc.convertBitcoins(ConverterSvc.Currency.USD, -1));
    }

    @Test
    public void convertBitcoins_NegativeBitCoinsToGBP_ThrowsException() throws IOException {
        // arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        // act
        ConverterSvc converterSvc = new ConverterSvc(client);
      
        // assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> converterSvc.convertBitcoins(ConverterSvc.Currency.GBP, -1));

    }

    @Test
    public void convertBitcoins_NegativeBitCoinsToEUR_ThrowsException() throws IOException {
        // arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        // act
        ConverterSvc converterSvc = new ConverterSvc(client);
      
        // assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> converterSvc.convertBitcoins(ConverterSvc.Currency.EUR, -1));

    }
    @Test
    public void convertBitcoins_503ServiceUnavailable_ReturnsCorrectErrorCode() throws IOException {
        // arrange
        when(statusLine.getStatusCode()).thenReturn(503);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        // act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(ConverterSvc.Currency.USD, 2);
      
        // assert
        double expected = -1;
        Assertions.assertEquals(expected, actual);
    }


}