package com.example.sales_buy.dto.info;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class SaleInfoDto {
    private Long id;
    private Long buyerId;
    private LocalDate saleDate;
    private BigDecimal total;
    private List<SaleDetailInfoDto>saleDetailInfoDtos;
}
