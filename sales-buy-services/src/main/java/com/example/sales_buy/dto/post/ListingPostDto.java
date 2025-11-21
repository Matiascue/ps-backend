package com.example.sales_buy.dto.post;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ListingPostDto {
    private Long userCardId;
    private Long userId;
    private int quantity;
    private BigDecimal unitaryPrice;
}
