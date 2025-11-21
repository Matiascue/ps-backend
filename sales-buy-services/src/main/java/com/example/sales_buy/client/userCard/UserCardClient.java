package com.example.sales_buy.client.userCard;

import lombok.Data;

@Data
public class UserCardClient {
    private Long userId;
    private Long cardId;
    private int quantity;
}
