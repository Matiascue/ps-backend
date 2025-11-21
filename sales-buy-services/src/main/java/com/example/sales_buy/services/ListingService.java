package com.example.sales_buy.services;

import com.example.sales_buy.dto.info.ListingInfoDto;
import com.example.sales_buy.dto.post.ListingPostDto;
import com.example.sales_buy.dto.put.ListingPutDto;

import java.util.List;

public interface ListingService {
    ListingInfoDto onSale(ListingPostDto listingPostDtos);
    ListingInfoDto getListingById(Long id);
    void updateListingBySell(ListingPutDto listingPutDto);
    ListingInfoDto updateListing(ListingPutDto listingPutDto);
    List<ListingInfoDto>getAllByUser(Long userId);
    List<ListingInfoDto>getAll();
}
