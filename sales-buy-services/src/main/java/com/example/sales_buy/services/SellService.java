package com.example.sales_buy.services;

import com.example.sales_buy.dto.info.SellsInfoDto;

import java.util.List;

public interface SellService {
    List<SellsInfoDto>getAllByUserId(Long userId);
}
