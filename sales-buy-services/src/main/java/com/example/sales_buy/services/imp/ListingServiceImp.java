package com.example.sales_buy.services.imp;

import com.example.sales_buy.dto.info.ListingInfoDto;
import com.example.sales_buy.dto.post.ListingPostDto;
import com.example.sales_buy.dto.put.ListingPutDto;
import com.example.sales_buy.entity.ListingEntity;
import com.example.sales_buy.repository.ListingRepository;
import com.example.sales_buy.services.ExternService;
import com.example.sales_buy.services.ListingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ListingServiceImp implements ListingService {
    private final ListingRepository listingRepository;
    private final ExternService externService;

    public ListingServiceImp(ListingRepository listingRepository,ExternService externService){
        this.listingRepository=listingRepository;
        this.externService=externService;
    }

    @Override
    public ListingInfoDto onSale(ListingPostDto listingPostDtos) {
            ListingEntity listing=new ListingEntity();
            listing.setUserId(listingPostDtos.getUserId());
            listing.setQuantity(listingPostDtos.getQuantity());
            listing.setUserCardId(listingPostDtos.getUserCardId());
            listing.setStatus("INSALE");
            listing.setUnitaryPrice(listingPostDtos.getUnitaryPrice());
            listing=this.listingRepository.save(listing);
            this.externService.sendListingEmail(listing.getUserId());
        return this.mapEntityToDto(listing);
    }

    @Override
    public ListingInfoDto getListingById(Long id) {
       ListingEntity l=this.listingRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("La oferta no se encontro"));
       return this.mapEntityToDto(l);
    }

    @Override
    public void updateListingBySell(ListingPutDto listingPutDto) {
        ListingEntity listing=this.listingRepository.findById(listingPutDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("La oferta no se encontro"));
        listing.setUnitaryPrice(listingPutDto.getUnitaryPrice());
        if(listing.getQuantity()<listingPutDto.getQuantity()){
            throw new IllegalArgumentException("La cantidad ingresada  no debe superar a la cantidad que se esta vendiendo");
        }
        listing.setQuantity(listing.getQuantity() - listingPutDto.getQuantity());
        if (listing.getQuantity() == 0) {
            listing.setStatus("SOLD");
        }
         this.listingRepository.save(listing);
    }

    @Override
    public ListingInfoDto updateListing(ListingPutDto listingPutDto) {
        ListingEntity listing=this.listingRepository.findById(listingPutDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("La oferta no se encontro"));
        listing.setUnitaryPrice(listingPutDto.getUnitaryPrice());
        listing.setQuantity(listingPutDto.getQuantity());
        listing.setStatus(listingPutDto.getStatus());
        listing=this.listingRepository.save(listing);
        return this.mapEntityToDto(listing);
    }

    @Override
    public List<ListingInfoDto> getAllByUser(Long userId) {
        return this.listingRepository.getAllByUserId(userId).stream()
                .map(this::mapEntityToDto)
                .toList();
    }

    @Override
    public List<ListingInfoDto> getAll() {
        return this.listingRepository.findAll().stream()
                .filter(listingEntity -> listingEntity.getStatus().equals("INSALE"))
                .map(this::mapEntityToDto)
                .toList();
    }

    private ListingInfoDto mapEntityToDto(ListingEntity listingEntity){
        ListingInfoDto l=new ListingInfoDto();
        l.setId(listingEntity.getId());
        l.setQuantity(listingEntity.getQuantity());
        l.setStatus(listingEntity.getStatus());
        l.setUnitaryPrice(listingEntity.getUnitaryPrice());
        l.setUserCardId(listingEntity.getUserCardId());
        l.setUserId(listingEntity.getUserId());
        return l;
    }
}
