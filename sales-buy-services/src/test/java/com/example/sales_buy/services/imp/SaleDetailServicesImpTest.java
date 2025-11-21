package com.example.sales_buy.services.imp;

import com.example.sales_buy.client.userCard.UserCardClient;
import com.example.sales_buy.client.userCard.UserCardClientService;
import com.example.sales_buy.dto.info.ListingInfoDto;
import com.example.sales_buy.dto.info.SaleDetailInfoDto;
import com.example.sales_buy.dto.post.SaleDetailPostDto;
import com.example.sales_buy.entity.ListingEntity;
import com.example.sales_buy.entity.SaleDetailEntity;
import com.example.sales_buy.entity.SaleEntity;
import com.example.sales_buy.repository.SaleDetailRepository;
import com.example.sales_buy.services.ListingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SaleDetailServicesImpTest {

    @InjectMocks
    private SaleDetailServicesImp saleDetailServicesImp;
    @Mock
    private UserCardClientService userCardClientService;
    @Mock
    private ListingService listingService;
    @Mock
    private SaleDetailRepository saleDetailRepository;

    private ListingEntity listingEntity;
    private ListingInfoDto listingInfoDto;
    private SaleDetailEntity saleDetailEntity;
    private UserCardClient userCardClient;
    private SaleEntity saleEntity;

    @BeforeEach
    void setUp(){
    saleEntity=new SaleEntity();
    saleEntity.setStatus("PURCHASED");
    saleEntity.setSaleDate(LocalDate.now());
    saleEntity.setId(1L);
    saleEntity.setBuyerId(1L);
    saleEntity.setTotalAmount(BigDecimal.valueOf(1000));

    listingEntity=new ListingEntity();
    listingEntity.setId(1L);
    listingEntity.setUserId(1L);
    listingEntity.setQuantity(2);
    listingEntity.setStatus("INSALE");
    listingEntity.setUserCardId(1L);
    listingEntity.setUnitaryPrice(BigDecimal.valueOf(1000));

    saleDetailEntity=new SaleDetailEntity();
    saleDetailEntity.setId(1L);
    saleDetailEntity.setSale(saleEntity);
    saleDetailEntity.setListing(listingEntity);
    saleDetailEntity.setSubtotal(BigDecimal.valueOf(1000));
    saleDetailEntity.setCardId(1L);
    saleDetailEntity.setQuantity(1);
    saleDetailEntity.setFromUserId(1L);
    saleDetailEntity.setUnitaryPrice(BigDecimal.valueOf(1000));

    listingInfoDto=new ListingInfoDto();
    listingInfoDto.setId(1L);
    listingInfoDto.setUserId(1L);
    listingInfoDto.setQuantity(1);
    listingInfoDto.setStatus("INSALE");
    listingInfoDto.setUserCardId(1L);
    listingInfoDto.setUnitaryPrice(BigDecimal.valueOf(1000));

    userCardClient=new UserCardClient();
    userCardClient.setCardId(1L);
    userCardClient.setQuantity(1);
    userCardClient.setUserId(1L);
    }

    @Test
    void registerDetails() {
        List<SaleDetailPostDto> dtoList = new ArrayList<>();
        SaleDetailPostDto s = new SaleDetailPostDto();
        s.setCardId(1L);
        s.setQuantity(1);
        s.setFromUserId(1L);
        s.setListingId(1L);
        dtoList.add(s);
        Mockito.when(listingService.getListingById(1L)).thenReturn(listingInfoDto);
        Mockito.when(userCardClientService.updateSellerQuantity(1L,1,1L)).thenReturn(userCardClient);
        Mockito.when(saleDetailRepository.save(Mockito.any(SaleDetailEntity.class))).thenReturn(saleDetailEntity);
        List<SaleDetailInfoDto>response=this.saleDetailServicesImp.registerDetails(dtoList,saleEntity);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals(1L,response.get(0).getId());
        Assertions.assertEquals(1,response.get(0).getQuantity());
        Assertions.assertEquals(BigDecimal.valueOf(1000),response.get(0).getSubtotal());
        Assertions.assertEquals(1L,response.get(0).getFromUserId());
    }

    @Test
    void getAllBySaleId() {
        List<SaleDetailEntity>list=new ArrayList<>();
        list.add(saleDetailEntity);
        Mockito.when(saleDetailRepository.findAllBySaleId(1L)).thenReturn(list);
        Mockito.when(listingService.getListingById(1L)).thenReturn(listingInfoDto);
        List<SaleDetailInfoDto>response=this.saleDetailServicesImp.getAllBySaleId(1L);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals(1L,response.get(0).getId());
        Assertions.assertEquals(1,response.get(0).getQuantity());
        Assertions.assertEquals(BigDecimal.valueOf(1000),response.get(0).getSubtotal());
        Assertions.assertEquals(1L,response.get(0).getFromUserId());
    }
}