package com.enigma.carrent.config;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Value("${rentcar_api.json_placeholder_url}")
    private String jsonPlaceholderBaseUrl;

    @Value("${midtrans.api.url}")
    private String midtransBaseApiUrl;

    @Value("${midtrans.server.key}")
    private String midtransServerKey;

    @Bean("jsonPlaceHolderClient")
    public RestClient jsonPlaceHolderClient() {
        return RestClient.builder().baseUrl(jsonPlaceholderBaseUrl).defaultHeader("Content-Type", "application/json").build();
    }


    @Bean("midtransPaymentGatewayClient")
    public RestClient midtransPaymentGatewayClient() {
        Base64.Encoder base64Encoder = Base64.getEncoder();
        String AUTH_STRING = base64Encoder.encodeToString((midtransServerKey + ":").getBytes());
        String authorizationHeader = "Basic " + AUTH_STRING;

        return RestClient.builder()
                .baseUrl(midtransBaseApiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, authorizationHeader)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
