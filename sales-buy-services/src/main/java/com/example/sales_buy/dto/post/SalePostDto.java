package com.example.sales_buy.dto.post;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SalePostDto {
    private Long buyerId;
    private LocalDate saleDate;
    private List<SaleDetailPostDto>saleDetailPostDtos;
}
