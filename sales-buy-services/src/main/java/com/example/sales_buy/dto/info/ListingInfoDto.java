package com.example.sales_buy.dto.info;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListingInfoDto {
    private Long id;
    private Long userCardId;
    private Long userId;
    private int quantity;
    private BigDecimal unitaryPrice;
    private String status;
}
