package com.example.cards.dto.post;

import lombok.Data;


@Data
public class UserCardsPostDto {
    private String userId;
    private CardPostDto cardPostDto;
    private int quantity;
}
