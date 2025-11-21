package com.example.sales_buy.controller;

import com.example.sales_buy.dto.info.ListingInfoDto;
import com.example.sales_buy.dto.info.SellsInfoDto;
import com.example.sales_buy.dto.post.ListingPostDto;
import com.example.sales_buy.dto.put.ListingPutDto;
import com.example.sales_buy.services.ListingService;
import com.example.sales_buy.services.SellService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/listing")
public class ListingController {
    private final ListingService listingService;
    private final SellService sellService;
    public ListingController(ListingService listingService,SellService sellService){
        this.listingService=listingService;
        this.sellService=sellService;
    }
    @PostMapping
    public ListingInfoDto onSale(@RequestBody ListingPostDto listingPostDtos){
        return this.listingService.onSale(listingPostDtos);
    }
    @PutMapping("/listing")
    public ListingInfoDto updateListing(@RequestBody ListingPutDto listingPutDto){
        return this.listingService.updateListing(listingPutDto);
    }
    @GetMapping("/getAll")
    public List<ListingInfoDto>getAll(){
        return this.listingService.getAll();
    }
    @GetMapping("/getAll/{userId}")
    public List<ListingInfoDto>getAllByUserId(@PathVariable Long userId){
        return this.listingService.getAllByUser(userId);
    }
    @GetMapping("/sells/{userId}")
    public List<SellsInfoDto>getAllSellsByUserId(@PathVariable Long userId){
        return this.sellService.getAllByUserId(userId);
    }
}
