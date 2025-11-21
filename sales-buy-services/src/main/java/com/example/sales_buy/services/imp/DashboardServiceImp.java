package com.example.sales_buy.services.imp;

import com.example.sales_buy.client.user.UserClientService;
import com.example.sales_buy.dto.report.SellerReportDto;
import com.example.sales_buy.dto.report.UserBuysReportDto;
import com.example.sales_buy.repository.SaleDetailRepository;
import com.example.sales_buy.repository.SaleRepository;
import com.example.sales_buy.services.DashboardService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardServiceImp implements DashboardService {

    private final SaleRepository saleRepository;
    private final UserClientService userClientService;
    private final SaleDetailRepository saleDetailRepository;

    public DashboardServiceImp(SaleRepository saleRepository,UserClientService userClientService,
                               SaleDetailRepository saleDetailRepository){
        this.saleRepository=saleRepository;
        this.userClientService=userClientService;
        this.saleDetailRepository=saleDetailRepository;
    }

    @Override
    public List<UserBuysReportDto> getTopBuyer(LocalDate from, LocalDate to) {
        List<Object[]>objects=this.saleRepository.findTopBuyerIdsWithCounts(from,to);
        return objects.stream().map(objects1 -> {
            Long buyerId=(Long)objects1[0];
            Long totalSales=(Long)objects1[1];
            BigDecimal totalAmount=(BigDecimal)objects1[2];
            String username=this.userClientService.getUser(buyerId).getUsername();
            return new UserBuysReportDto(buyerId,username,totalSales,totalAmount);
        }).toList();
    }

    @Override
    public List<SellerReportDto> getTopSellers(LocalDate from, LocalDate to) {
        List<Object[]>result=this.saleDetailRepository.findTopSellerIdsWithTotals(from,to);
        return result.stream().map(res->{
            Long sellerId=(Long)res[0];
            BigDecimal total=(BigDecimal)res[1];
            Long totalQuantity=((Number)res[2]).longValue();
            String username=this.userClientService.getUser(sellerId).getUsername();
            return new SellerReportDto(sellerId,total,totalQuantity,username);
        }).toList();
    }

    @Override
    public UserBuysReportDto getUserBuysReport(LocalDate from, LocalDate to, Long userId) {
        List<Object[]> results=this.saleRepository.findBuyerTotalsByUserId(from,to,userId);
        if (results.isEmpty()) {
            return new UserBuysReportDto(userId, this.userClientService.getUser(userId).getUsername(), 0L, BigDecimal.ZERO);
        }

        Object[] row = results.get(0);
        Long buyerId = (Long) row[0];
        Long total = (Long) row[1];
        BigDecimal totalAmount = (BigDecimal) row[2];
        String username = this.userClientService.getUser(buyerId).getUsername();

        return new UserBuysReportDto(buyerId,username,total,totalAmount);
    }

    @Override
    public SellerReportDto getSellerReport(LocalDate from, LocalDate to, Long userId) {
        List<Object[]> objects=this.saleDetailRepository.findSellerTotalsByUserId(from, to, userId);
        if (objects.isEmpty()) {
            return new SellerReportDto(userId, BigDecimal.ZERO,0L, this.userClientService.getUser(userId).getUsername());
        }
        Object[] row = objects.get(0);
        Long sellerId=(Long)row[0];
        BigDecimal total=(BigDecimal)row[1];
        Long totalQuantity=((Number)row[2]).longValue();
        String username=this.userClientService.getUser(sellerId).getUsername();
        return new SellerReportDto(sellerId,total,totalQuantity,username);
    }
}
