package com.example.sales_buy.dto.put;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListingPutDto {
    private Long id;
    private int quantity;
    private BigDecimal unitaryPrice;
    private String status;
}
