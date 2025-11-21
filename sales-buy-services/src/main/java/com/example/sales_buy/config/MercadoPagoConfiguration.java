package com.example.sales_buy.config;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPConfException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoConfiguration {
    @Value("${mercadopago.access.token}")
    private String accessToken;

    @PostConstruct
    public void init() throws MPConfException {
        System.out.println("Access Token leído: " + accessToken);
        MercadoPago.SDK.setAccessToken(accessToken);
    }

    }
