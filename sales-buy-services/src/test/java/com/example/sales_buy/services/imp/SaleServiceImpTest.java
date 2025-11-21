package com.example.sales_buy.services.imp;

import com.example.sales_buy.client.userCard.UserCardClient;
import com.example.sales_buy.client.userCard.UserCardClientService;
import com.example.sales_buy.dto.info.ListingInfoDto;
import com.example.sales_buy.dto.info.SaleDetailInfoDto;
import com.example.sales_buy.dto.info.SaleInfoDto;
import com.example.sales_buy.dto.post.SaleDetailPostDto;
import com.example.sales_buy.dto.post.SalePostDto;
import com.example.sales_buy.entity.SaleEntity;
import com.example.sales_buy.repository.SaleRepository;
import com.example.sales_buy.services.SaleDetailService;
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
class SaleServiceImpTest {

    @InjectMocks
    private SaleServiceImp saleServiceImp;
    @Mock
    private SaleRepository saleRepository;
    @Mock
    private SaleDetailService saleDetailService;
    @Mock
    private UserCardClientService userCardClientService;

    private ListingInfoDto listingInfoDto;
    private UserCardClient userCardClient;
    private SaleEntity saleEntity;
    private SaleEntity saleEntityPurchased;
    private SaleDetailInfoDto saleDetailInfoDto;

    @BeforeEach
    void setUp(){
        saleEntity=new SaleEntity();
        saleEntity.setStatus("INPROCESS");
        saleEntity.setSaleDate(LocalDate.now());
        saleEntity.setId(1L);
        saleEntity.setBuyerId(1L);
        saleEntity.setTotalAmount(BigDecimal.valueOf(1000));

        saleEntityPurchased=new SaleEntity();
        saleEntityPurchased.setStatus("PURCHASED");
        saleEntityPurchased.setSaleDate(LocalDate.now());
        saleEntityPurchased.setId(1L);
        saleEntityPurchased.setBuyerId(1L);
        saleEntityPurchased.setTotalAmount(BigDecimal.valueOf(1000));

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

        saleDetailInfoDto=new SaleDetailInfoDto();
        saleDetailInfoDto.setListingInfoDto(listingInfoDto);
        saleDetailInfoDto.setId(1L);
        saleDetailInfoDto.setSubtotal(BigDecimal.valueOf(1000));
        saleDetailInfoDto.setQuantity(1);
        saleDetailInfoDto.setFromUserId(1L);
        saleDetailInfoDto.setUnitaryPrice(BigDecimal.valueOf(1000));
        saleDetailInfoDto.setCardId(1L);
    }

    @Test
    void buyCards() {
        List<SaleDetailPostDto> dtoList = new ArrayList<>();
        SaleDetailPostDto s = new SaleDetailPostDto();
        s.setCardId(1L);
        s.setQuantity(1);
        s.setFromUserId(1L);
        s.setListingId(1L);
        dtoList.add(s);
        SalePostDto sale=new SalePostDto();
        sale.setSaleDate(LocalDate.now());
        sale.setBuyerId(1L);
        sale.setSaleDetailPostDtos(dtoList);
        List<SaleDetailInfoDto>saleDetailInfoDtoList=new ArrayList<>();
        saleDetailInfoDtoList.add(saleDetailInfoDto);
        Mockito.when(saleDetailService.registerDetails(Mockito.eq(dtoList), Mockito.any(SaleEntity.class))).thenReturn(saleDetailInfoDtoList);
        Mockito.when(userCardClientService.updateBuyerQuantity(1L,1,1L)).thenReturn(userCardClient);
        Mockito.when(saleRepository.save(Mockito.any(SaleEntity.class))).thenReturn(saleEntityPurchased);
        SaleInfoDto response=this.saleServiceImp.buyCards(sale);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L,response.getId());
        Assertions.assertEquals(BigDecimal.valueOf(1000),response.getTotal());
        Assertions.assertEquals(saleDetailInfoDtoList,response.getSaleDetailInfoDtos());
    }

    @Test
    void getAllByUserId() {
        List<SaleDetailInfoDto>saleDetailInfoDtoList=new ArrayList<>();
        saleDetailInfoDtoList.add(saleDetailInfoDto);
        List<SaleEntity>saleInfoDtoList=new ArrayList<>();
        saleInfoDtoList.add(saleEntityPurchased);
        Mockito.when(saleRepository.findAllByBuyerId(1L)).thenReturn(saleInfoDtoList);
        Mockito.when(saleDetailService.getAllBySaleId(1L)).thenReturn(saleDetailInfoDtoList);
        List<SaleInfoDto>response=this.saleServiceImp.getAllByUserId(1L);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals(1L,response.get(0).getId());
        Assertions.assertEquals(BigDecimal.valueOf(1000),response.get(0).getTotal());
    }
}