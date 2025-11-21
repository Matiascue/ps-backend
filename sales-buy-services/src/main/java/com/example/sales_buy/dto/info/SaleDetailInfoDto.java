package com.example.sales_buy.dto.info;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleDetailInfoDto {
    private Long id;
    private ListingInfoDto listingInfoDto;
    private Long cardId;
    private Long fromUserId;
    private int quantity;
    private BigDecimal unitaryPrice;
    private BigDecimal subtotal;
}
