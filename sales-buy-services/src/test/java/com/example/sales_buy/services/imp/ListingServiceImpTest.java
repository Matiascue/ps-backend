package com.example.sales_buy.services.imp;

import com.example.sales_buy.dto.info.ListingInfoDto;
import com.example.sales_buy.dto.post.ListingPostDto;
import com.example.sales_buy.dto.put.ListingPutDto;
import com.example.sales_buy.entity.ListingEntity;
import com.example.sales_buy.repository.ListingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ListingServiceImpTest {

    @InjectMocks
    private ListingServiceImp listingServiceImp;
    @Mock
    private ListingRepository listingRepository;

    private ListingEntity listing;
    private ListingEntity listingUpdate;
    @BeforeEach
    void setUp(){
        listing=new ListingEntity();
        listing.setId(1L);
        listing.setUserCardId(1L);
        listing.setUserId(1L);
        listing.setQuantity(3);
        listing.setStatus("INSALE");
        listing.setUnitaryPrice(BigDecimal.valueOf(1000));
        listingUpdate=new ListingEntity();
        listingUpdate.setId(2L);
        listingUpdate.setUserCardId(1L);
        listingUpdate.setUserId(1L);
        listingUpdate.setQuantity(0);
        listingUpdate.setStatus("SOLD");
        listingUpdate.setUnitaryPrice(BigDecimal.valueOf(1000));
    }

    @Test
    void onSale() {
        ListingPostDto listingPostDto=new ListingPostDto();
        listingPostDto.setUserId(1L);
        listingPostDto.setQuantity(3);
        listingPostDto.setUserCardId(1L);
        listingPostDto.setUnitaryPrice(BigDecimal.valueOf(1000));
        Mockito.when(listingRepository.save(Mockito.any(ListingEntity.class))).thenReturn(listing);
        ListingInfoDto response=this.listingServiceImp.onSale(listingPostDto);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L,response.getId());
        Assertions.assertEquals(1L,response.getUserId());
        Assertions.assertEquals(1L,response.getUserCardId());
        Assertions.assertEquals(3,response.getQuantity());
        Assertions.assertEquals("INSALE",response.getStatus());
    }

    @Test
    void getListingById() {
        Mockito.when(listingRepository.findById(1L)).thenReturn(Optional.of(listing));
        ListingInfoDto response=this.listingServiceImp.getListingById(1L);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L,response.getId());
        Assertions.assertEquals(1L,response.getUserId());
        Assertions.assertEquals(1L,response.getUserCardId());
        Assertions.assertEquals(3,response.getQuantity());
        Assertions.assertEquals("INSALE",response.getStatus());
    }
    @Test
    void getListingByIdNotFound(){
        Mockito.when(listingRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException response=Assertions.assertThrows(EntityNotFoundException.class,()->{
           this.listingServiceImp.getListingById(1L);
        });
        Assertions.assertNotNull(response);
        Assertions.assertEquals("La oferta no se encontro",response.getMessage());
    }
    @Test
    void updateListingBySell() {
        listingUpdate.setQuantity(3);
        ListingPutDto listingPutDto=new ListingPutDto();
        listingPutDto.setStatus("SOLD");
        listingPutDto.setId(2L);
        listingPutDto.setUnitaryPrice(BigDecimal.valueOf(1000));
        listingPutDto.setQuantity(3);
        Mockito.when(listingRepository.findById(2L)).thenReturn(Optional.of(listingUpdate));
        Mockito.when(listingRepository.save(Mockito.any(ListingEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        this.listingServiceImp.updateListingBySell(listingPutDto);
        assertEquals(0, listingUpdate.getQuantity());
        assertEquals("SOLD", listingUpdate.getStatus());
        assertEquals(BigDecimal.valueOf(1000), listingUpdate.getUnitaryPrice());
    }
    @Test
    void updateListingBySellNotFound(){
        ListingPutDto listingPutDto=new ListingPutDto();
        listingPutDto.setStatus("SOLD");
        listingPutDto.setId(2L);
        listingPutDto.setUnitaryPrice(BigDecimal.valueOf(1000));
        listingPutDto.setQuantity(0);
        Mockito.when(listingRepository.findById(2L)).thenReturn(Optional.empty());
        EntityNotFoundException response=Assertions.assertThrows(EntityNotFoundException.class,()->{
            this.listingServiceImp.updateListingBySell(listingPutDto);
        });
        Assertions.assertNotNull(response);
        Assertions.assertEquals("La oferta no se encontro",response.getMessage());
    }
    @Test
    void updateListingBySell_QuantityExceedsAvailable() {
        listingUpdate.setQuantity(2);
        ListingPutDto listingPutDto = new ListingPutDto();
        listingPutDto.setId(2L);
        listingPutDto.setQuantity(3);
        listingPutDto.setUnitaryPrice(BigDecimal.valueOf(1000));
        Mockito.when(listingRepository.findById(2L)).thenReturn(Optional.of(listingUpdate));
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            listingServiceImp.updateListingBySell(listingPutDto);
        });
        Assertions.assertNotNull(exception);
        Assertions.assertEquals("La cantidad ingresada  no debe superar a la cantidad que se esta vendiendo", exception.getMessage());
    }
    @Test
    void updateListing() {
        ListingPutDto listingPutDto=new ListingPutDto();
        listingPutDto.setStatus("SOLD");
        listingPutDto.setId(2L);
        listingPutDto.setUnitaryPrice(BigDecimal.valueOf(1000));
        listingPutDto.setQuantity(0);
        Mockito.when(listingRepository.findById(2L)).thenReturn(Optional.of(listingUpdate));
        Mockito.when(listingRepository.save(Mockito.any(ListingEntity.class))).thenReturn(listingUpdate);
        ListingInfoDto response=this.listingServiceImp.updateListing(listingPutDto);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(2L,response.getId());
        Assertions.assertEquals(1L,response.getUserId());
        Assertions.assertEquals(1L,response.getUserCardId());
        Assertions.assertEquals(0,response.getQuantity());
        Assertions.assertEquals("SOLD",response.getStatus());
    }

    @Test
    void updateListingNotFound(){
        ListingPutDto listingPutDto=new ListingPutDto();
        listingPutDto.setStatus("SOLD");
        listingPutDto.setId(2L);
        listingPutDto.setUnitaryPrice(BigDecimal.valueOf(1000));
        listingPutDto.setQuantity(0);
        Mockito.when(listingRepository.findById(2L)).thenReturn(Optional.empty());
        EntityNotFoundException response=Assertions.assertThrows(EntityNotFoundException.class,()->{
            this.listingServiceImp.updateListing(listingPutDto);
        });
        Assertions.assertNotNull(response);
        Assertions.assertEquals("La oferta no se encontro",response.getMessage());
    }
    @Test
    void getAllByUser() {
        List<ListingEntity>listingEntityList=new ArrayList<>();
        listingEntityList.add(listing);
        Mockito.when(listingRepository.getAllByUserId(1L)).thenReturn(listingEntityList);
        List<ListingInfoDto>response=this.listingServiceImp.getAllByUser(1L);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals(1L,response.get(0).getId());
        Assertions.assertEquals(1L,response.get(0).getUserId());
        Assertions.assertEquals(1L,response.get(0).getUserCardId());
        Assertions.assertEquals(3,response.get(0).getQuantity());
        Assertions.assertEquals("INSALE",response.get(0).getStatus());
    }

    @Test
    void getAll() {
        List<ListingEntity>listingEntityList=new ArrayList<>();
        listingEntityList.add(listing);
        Mockito.when(listingRepository.findAll()).thenReturn(listingEntityList);
        List<ListingInfoDto>response=this.listingServiceImp.getAll();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals(1L,response.get(0).getId());
        Assertions.assertEquals(1L,response.get(0).getUserId());
        Assertions.assertEquals(1L,response.get(0).getUserCardId());
        Assertions.assertEquals(3,response.get(0).getQuantity());
        Assertions.assertEquals("INSALE",response.get(0).getStatus());
    }
}