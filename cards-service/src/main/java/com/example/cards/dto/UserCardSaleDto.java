package com.example.cards.dto;

import lombok.Data;

@Data
public class UserCardSaleDto {
    private Long userId;
    private Long cardId;
    private int quantity;
}
