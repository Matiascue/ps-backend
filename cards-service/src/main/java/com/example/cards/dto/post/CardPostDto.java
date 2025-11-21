package com.example.cards.dto.post;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardPostDto {
    private String code;
    private String name;
    private String superType;
    private String image_url;
    private String rarity;
    private BigDecimal price;
}
