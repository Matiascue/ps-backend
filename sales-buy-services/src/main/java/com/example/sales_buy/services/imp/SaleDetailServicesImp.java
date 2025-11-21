package com.example.sales_buy.services.imp;

import com.example.sales_buy.client.mail.MailClientService;
import com.example.sales_buy.client.userCard.UserCardClientService;
import com.example.sales_buy.dto.info.ListingInfoDto;
import com.example.sales_buy.dto.info.SaleDetailInfoDto;
import com.example.sales_buy.dto.post.SaleDetailPostDto;
import com.example.sales_buy.dto.put.ListingPutDto;
import com.example.sales_buy.entity.ListingEntity;
import com.example.sales_buy.entity.SaleDetailEntity;
import com.example.sales_buy.entity.SaleEntity;
import com.example.sales_buy.repository.SaleDetailRepository;
import com.example.sales_buy.services.ExternService;
import com.example.sales_buy.services.ListingService;
import com.example.sales_buy.services.SaleDetailService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleDetailServicesImp implements SaleDetailService {

    private final UserCardClientService userCardClientService;
    private final ListingService listingService;
    private final SaleDetailRepository saleDetailRepository;
    private final ExternService externService;

    public SaleDetailServicesImp(UserCardClientService userCardClientService
            ,ListingService listingService
    ,SaleDetailRepository saleDetailRepository,ExternService externService){
        this.listingService=listingService;
        this.saleDetailRepository=saleDetailRepository;
        this.userCardClientService=userCardClientService;
        this.externService=externService;
    }

    @Override
    public List<SaleDetailInfoDto> registerDetails(List<SaleDetailPostDto> saleDetailPostDtos, SaleEntity sale) {
        List<SaleDetailInfoDto>detailInfoDtos=new ArrayList<>();
        for(SaleDetailPostDto d:saleDetailPostDtos){
            SaleDetailEntity saleDetailEntity=new SaleDetailEntity();
            ListingEntity l=this.getListingById(d.getListingId());
            saleDetailEntity.setSale(sale);
            saleDetailEntity.setQuantity(d.getQuantity());
            saleDetailEntity.setCardId(d.getCardId());
            saleDetailEntity.setListing(l);
            saleDetailEntity.setUnitaryPrice(l.getUnitaryPrice());
            saleDetailEntity.setFromUserId(d.getFromUserId());
            saleDetailEntity.setSubtotal(this.calculateSubtotal(d.getQuantity(),l.getUnitaryPrice()));
            this.updateListing(saleDetailEntity);
            this.userCardClientService.updateSellerQuantity(l.getUserId(),d.getQuantity(),d.getCardId());
            this.externService.sendMailExtern(sale.getBuyerId(), d.getQuantity(),l.getUserId(),d.getCardId());
            saleDetailEntity=this.saleDetailRepository.save(saleDetailEntity);
            detailInfoDtos.add(this.mapEntityToDto(saleDetailEntity));
        }
        return detailInfoDtos;
    }

    @Override
    public List<SaleDetailInfoDto> getAllBySaleId(Long saleId) {
        return this.saleDetailRepository.findAllBySaleId(saleId).stream()
                .map(this::mapEntityToDto)
                .toList();
    }

    private ListingEntity getListingById(Long id){
        ListingEntity l=new ListingEntity();
        ListingInfoDto ld=this.listingService.getListingById(id);
        l.setId(ld.getId());
        l.setStatus(ld.getStatus());
        l.setQuantity(ld.getQuantity());
        l.setUserId(ld.getUserId());
        l.setUnitaryPrice(ld.getUnitaryPrice());
        l.setUserCardId(ld.getUserCardId());
        return l;
    }
    private BigDecimal calculateSubtotal(int quantity,BigDecimal unitaryPrice) {
       return unitaryPrice.multiply(BigDecimal.valueOf(quantity));
    }
    private void updateListing(SaleDetailEntity saleDetail){
        ListingPutDto l=new ListingPutDto();
        l.setId(saleDetail.getListing().getId());
        l.setQuantity(saleDetail.getQuantity());
        l.setUnitaryPrice(saleDetail.getListing().getUnitaryPrice());
        l.setStatus(saleDetail.getListing().getStatus());
        this.listingService.updateListingBySell(l);
    }
    private SaleDetailInfoDto mapEntityToDto(SaleDetailEntity saleDetailEntity){
        SaleDetailInfoDto s=new SaleDetailInfoDto();
        s.setId(saleDetailEntity.getId());
        s.setFromUserId(saleDetailEntity.getFromUserId());
        s.setQuantity(saleDetailEntity.getQuantity());
        s.setSubtotal(saleDetailEntity.getSubtotal());
        s.setUnitaryPrice(saleDetailEntity.getUnitaryPrice());
        s.setCardId(saleDetailEntity.getCardId());
        s.setListingInfoDto(this.listingService.getListingById(saleDetailEntity.getListing().getId()));
        return s;
    }
}
