package com.example.sales_buy.services;

import com.example.sales_buy.dto.report.SellerReportDto;
import com.example.sales_buy.dto.report.UserBuysReportDto;

import java.time.LocalDate;
import java.util.List;

public interface DashboardService {
    List<UserBuysReportDto>getTopBuyer(LocalDate from, LocalDate to);
    List<SellerReportDto>getTopSellers(LocalDate from,LocalDate to);
    UserBuysReportDto getUserBuysReport(LocalDate from, LocalDate to,Long userId);
    SellerReportDto getSellerReport(LocalDate from, LocalDate to,Long userId);
}
