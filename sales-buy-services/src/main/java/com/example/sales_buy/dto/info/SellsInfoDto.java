package com.example.sales_buy.dto.info;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SellsInfoDto {
    private Long listingId;
    private Long userId;
    private int quantity;
    private BigDecimal unitaryPrice;
    private String status;
    private List<SellsDetailInfoDto>sellsDetailInfoDtos;
}
