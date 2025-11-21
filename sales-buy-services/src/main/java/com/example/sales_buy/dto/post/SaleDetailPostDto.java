package com.example.sales_buy.dto.post;

import lombok.Data;

@Data
public class SaleDetailPostDto {
    private Long listingId;
    private Long cardId;
    private Long fromUserId;
    private int quantity;
}
