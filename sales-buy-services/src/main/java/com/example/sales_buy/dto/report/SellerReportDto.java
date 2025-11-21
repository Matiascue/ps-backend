package com.example.sales_buy.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerReportDto {
  private Long userId;
  private BigDecimal totalSold;
  private Long totalItemsSold;
  private String username;
}
