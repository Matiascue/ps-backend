package com.example.sales_buy.dto.info;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SellsDetailInfoDto {
    private Long cardId;
    private Long buyerId;
    private int quantity;
    private BigDecimal unitaryPrice;
    private BigDecimal subtotal;
}
