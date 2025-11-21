package com.example.sales_buy.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBuysReportDto {
    private Long buyerId;
    private String username;
    private Long totalSales;
    private BigDecimal totalAmount;
}
