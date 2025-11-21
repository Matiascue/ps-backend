package com.example.sales_buy.services;

import com.example.sales_buy.dto.info.SaleInfoDto;
import com.example.sales_buy.dto.post.SalePostDto;

import java.util.List;

public interface SaleService {
    SaleInfoDto buyCards(SalePostDto salePostDto);
    List<SaleInfoDto>getAllByUserId(Long userId);
}
