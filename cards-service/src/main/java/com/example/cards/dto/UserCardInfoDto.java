package com.example.cards.dto;

import lombok.Data;

@Data
public class UserCardInfoDto {
    private Long id;
    private Long userId;
    private CardInfoDto cardInfoDto;
    private int quantity;
}
