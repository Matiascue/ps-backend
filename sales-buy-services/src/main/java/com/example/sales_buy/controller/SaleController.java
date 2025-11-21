package com.example.sales_buy.controller;

import com.example.sales_buy.dto.info.SaleInfoDto;
import com.example.sales_buy.dto.post.SalePostDto;
import com.example.sales_buy.dto.report.SellerReportDto;
import com.example.sales_buy.dto.report.UserBuysReportDto;
import com.example.sales_buy.services.DashboardService;
import com.example.sales_buy.services.SaleService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/sale")
public class SaleController {
    private final SaleService saleService;
    private final DashboardService dashboardService;
    public SaleController(SaleService saleService,DashboardService dashboardService){
        this.saleService=saleService;
        this.dashboardService=dashboardService;
    }
    @PostMapping
    public SaleInfoDto buyCards(@RequestBody SalePostDto salePostDto){
        return this.saleService.buyCards(salePostDto);
    }
    @GetMapping("/{userId}")
    public List<SaleInfoDto>getAllByUserId(@PathVariable Long userId){
        return this.saleService.getAllByUserId(userId);
    }
    @GetMapping("/top-buyers")
    public List<UserBuysReportDto>getAllBuyers(@RequestParam LocalDate from,@RequestParam LocalDate to){
        return this.dashboardService.getTopBuyer(from,to);
    }
    @GetMapping("/top-sellers")
    public List<SellerReportDto>getTopSeller(@RequestParam LocalDate from, @RequestParam LocalDate to){
        return this.dashboardService.getTopSellers(from,to);
    }
    @GetMapping("/my-sells")
    public  SellerReportDto getSellsByUserId(@RequestParam LocalDate from, @RequestParam LocalDate to,@RequestParam Long userId){
        return this.dashboardService.getSellerReport(from, to, userId);
    }
    @GetMapping("/my-buys")
    public  UserBuysReportDto getBuysByUserId(@RequestParam LocalDate from, @RequestParam LocalDate to,@RequestParam Long userId){
        return this.dashboardService.getUserBuysReport(from, to, userId);
    }
}
