package com.example.sales_buy.services.imp;

import com.example.sales_buy.client.userCard.UserCardClientService;
import com.example.sales_buy.dto.info.SaleDetailInfoDto;
import com.example.sales_buy.dto.info.SaleInfoDto;
import com.example.sales_buy.dto.post.SalePostDto;
import com.example.sales_buy.entity.SaleEntity;
import com.example.sales_buy.repository.SaleRepository;
import com.example.sales_buy.services.SaleDetailService;
import com.example.sales_buy.services.SaleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleServiceImp implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleDetailService saleDetailService;
    private final UserCardClientService userCardClientService;
    public SaleServiceImp(SaleRepository saleRepository
    ,SaleDetailService saleDetailService,UserCardClientService userCardClientService){
        this.saleDetailService=saleDetailService;
        this.saleRepository=saleRepository;
        this.userCardClientService=userCardClientService;
    }

    @Override
    @Transactional
    public SaleInfoDto buyCards(SalePostDto salePostDto) {
        SaleEntity sale=new SaleEntity();
        sale.setBuyerId(salePostDto.getBuyerId());
        sale.setSaleDate(salePostDto.getSaleDate());
        sale.setStatus("INPROCESS");
        sale=this.saleRepository.save(sale);
        SaleInfoDto saleInfoDto=this.registerFirstStep(sale);
        List<SaleDetailInfoDto>saleDetailInfoDtoList=this.saleDetailService.registerDetails(salePostDto.getSaleDetailPostDtos(),sale);
        saleInfoDto.setSaleDetailInfoDtos(saleDetailInfoDtoList);
        sale.setTotalAmount(this.calculateTotal(saleInfoDto.getSaleDetailInfoDtos()));
        this.sendUpdateToUserCardService(saleInfoDto.getSaleDetailInfoDtos(),sale);
        sale.setStatus("PURCHASED");
        sale=this.saleRepository.save(sale);
        saleInfoDto.setTotal(sale.getTotalAmount());
        return saleInfoDto;
    }

    @Override
    public List<SaleInfoDto> getAllByUserId(Long userId) {
        List<SaleInfoDto>dtoList=new ArrayList<>();
        List<SaleEntity>saleEntities=this.saleRepository.findAllByBuyerId(userId);
        for(SaleEntity s:saleEntities){
            SaleInfoDto sale=new SaleInfoDto();
            sale.setId(s.getId());
            sale.setTotal(s.getTotalAmount());
            sale.setSaleDate(s.getSaleDate());
            sale.setBuyerId(s.getBuyerId());
            sale.setSaleDetailInfoDtos(this.saleDetailService.getAllBySaleId(s.getId()));
            dtoList.add(sale);
        }
        return dtoList;
    }

    private SaleInfoDto registerFirstStep(SaleEntity sale){
     SaleInfoDto saleInfoDto=new SaleInfoDto();
     saleInfoDto.setId(sale.getId());
     saleInfoDto.setSaleDate(sale.getSaleDate());
     saleInfoDto.setBuyerId(sale.getBuyerId());
     return saleInfoDto;
    }
    private BigDecimal calculateTotal(List<SaleDetailInfoDto> s){
        BigDecimal total=new BigDecimal(0);
        for(SaleDetailInfoDto dto:s){
            total=total.add(dto.getSubtotal());
        }
        return total;
    }
    private void sendUpdateToUserCardService(List<SaleDetailInfoDto>sd,SaleEntity s){
        for(SaleDetailInfoDto dto:sd){
            try {
                this.userCardClientService.updateBuyerQuantity(s.getBuyerId(),dto.getQuantity(),dto.getCardId());
            }
            catch (Exception e){
                s.setStatus("CANCELLED");
                this.saleRepository.save(s);
                throw  e;
            }
        }
    }
}
