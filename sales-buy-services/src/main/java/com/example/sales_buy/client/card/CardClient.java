package com.example.sales_buy.client.card;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CardClient {
    private Long id;
    private String code;
    private String name;
    private String superType;
    private String image_url;
    private String rarity;
    private BigDecimal price;
    private LocalDate created_At;
}
