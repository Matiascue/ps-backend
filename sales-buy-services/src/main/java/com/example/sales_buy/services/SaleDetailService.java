package com.example.sales_buy.services;

import com.example.sales_buy.dto.info.SaleDetailInfoDto;
import com.example.sales_buy.dto.post.SaleDetailPostDto;
import com.example.sales_buy.entity.SaleEntity;

import java.util.List;

public interface SaleDetailService {
    List<SaleDetailInfoDto>registerDetails(List<SaleDetailPostDto>saleDetailPostDtos, SaleEntity sale);
    List<SaleDetailInfoDto>getAllBySaleId(Long saleId);
}
