package com.example.sales_buy.services.imp;

import com.example.sales_buy.dto.info.ListingInfoDto;
import com.example.sales_buy.dto.info.SellsDetailInfoDto;
import com.example.sales_buy.dto.info.SellsInfoDto;
import com.example.sales_buy.entity.SaleDetailEntity;
import com.example.sales_buy.repository.SaleDetailRepository;
import com.example.sales_buy.services.ListingService;
import com.example.sales_buy.services.SellService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class    SellServiceImp implements SellService {
    private final ListingService listingService;
    private final SaleDetailRepository saleDetailRepository;
    
    public SellServiceImp(ListingService listingService,SaleDetailRepository saleDetailRepository){
        this.listingService=listingService;
        this.saleDetailRepository=saleDetailRepository;
    }
    @Override
    public List<SellsInfoDto> getAllByUserId(Long userId) {
        List<SellsInfoDto>sellsInfoDtos=this.sellsBuild(userId);
        for (SellsInfoDto s:sellsInfoDtos){
            s.setSellsDetailInfoDtos(this.sellDetail(s.getListingId()));
        }
        return sellsInfoDtos;
    }

    private List<SellsDetailInfoDto> sellDetail(Long listingId) {
        List<SellsDetailInfoDto>dtos=new ArrayList<>();
        List<SaleDetailEntity>saleDetailEntities=this.saleDetailRepository.findAllByListingId(listingId);
        for(SaleDetailEntity sd:saleDetailEntities){
            SellsDetailInfoDto s=new SellsDetailInfoDto();
            s.setCardId(sd.getCardId());
            s.setSubtotal(sd.getSubtotal());
            s.setBuyerId(sd.getSale().getBuyerId());
            s.setQuantity(sd.getQuantity());
            s.setUnitaryPrice(sd.getUnitaryPrice());
            dtos.add(s);
        }
        return dtos;
    }

    private List<SellsInfoDto> sellsBuild(Long userId) {
        List<SellsInfoDto>dtos=new ArrayList<>();
        List<ListingInfoDto>list=this.listingService.getAllByUser(userId);
        for(ListingInfoDto l:list){
            SellsInfoDto s=new SellsInfoDto();
            s.setListingId(l.getId());
            s.setQuantity(l.getQuantity());
            s.setUnitaryPrice(l.getUnitaryPrice());
            s.setStatus(l.getStatus());
            s.setUserId(l.getUserId());
            dtos.add(s);
        }
        return dtos;
    }
}
