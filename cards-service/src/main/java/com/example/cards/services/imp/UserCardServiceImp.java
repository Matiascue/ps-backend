package com.example.cards.services.imp;

import com.example.cards.dto.CardInfoDto;
import com.example.cards.dto.UserCardInfoDto;
import com.example.cards.dto.UserCardSaleDto;
import com.example.cards.dto.post.UserCardsPostDto;
import com.example.cards.dto.update.UserCardUpdate;
import com.example.cards.entity.CardEntity;
import com.example.cards.entity.UserCardEntity;
import com.example.cards.jwt.JwtUtil;
import com.example.cards.repository.UserCardRepository;
import com.example.cards.services.CardService;
import com.example.cards.services.UserCardService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserCardServiceImp implements UserCardService {

    private final UserCardRepository userCardRepository;
    private final CardService cardService;
    private final JwtUtil jwtUtil;
    public UserCardServiceImp(UserCardRepository userCardRepository,CardService cardService
    ,JwtUtil jwtUtil){
        this.cardService=cardService;
        this.userCardRepository=userCardRepository;
        this.jwtUtil=jwtUtil;
    }

    @Override
    public List<UserCardInfoDto> registerUserCards(List<UserCardsPostDto> userCardsPostDtos) {
        List<UserCardInfoDto>userCardInfoDtos=new ArrayList<>();
        for(UserCardsPostDto uc:userCardsPostDtos){
            Long userId=this.jwtUtil.extractUserId(uc.getUserId());
            CardInfoDto cardInfoDto= this.cardService.getByCode(uc.getCardPostDto().getCode());
            Optional<UserCardEntity>userCardEntityOptional=this.userCardRepository.findByCard_CodeAndUserId(uc.getCardPostDto().getCode(), userId);
            if(userCardEntityOptional.isPresent()){
                UserCardEntity ucs=userCardEntityOptional.get();
                ucs.setQuantity(uc.getQuantity()+uc.getQuantity());
                ucs=this.userCardRepository.save(ucs);
                UserCardInfoDto userCardInfoDto=this.mapEntityToDtoUserCard(ucs);
                userCardInfoDto.setCardInfoDto(cardInfoDto);
                userCardInfoDtos.add(userCardInfoDto);
            }
            else {
                UserCardEntity userCardEntity = new UserCardEntity();
                userCardEntity.setUserId(userId);
                userCardEntity.setQuantity(uc.getQuantity());
                userCardEntity.setCard(this.maptDtoToEntityCard(cardInfoDto));
                userCardEntity = this.userCardRepository.save(userCardEntity);
                UserCardInfoDto userCardInfoDto = this.mapEntityToDtoUserCard(userCardEntity);
                userCardInfoDto.setCardInfoDto(cardInfoDto);
                userCardInfoDtos.add(userCardInfoDto);
            }
        }
        return userCardInfoDtos;
    }

    @Override
    public List<UserCardInfoDto> getAllByUserId(Long userId) {
        List<UserCardInfoDto>userCardInfoDtos=new ArrayList<>();
        List<UserCardEntity>userCardEntityList=userCardRepository.findAllByUserId(userId);
        for(UserCardEntity uc:userCardEntityList){
            UserCardInfoDto userCardInfoDto=this.mapEntityToDtoUserCard(uc);
            userCardInfoDto.setCardInfoDto(this.cardService.getById(uc.getCard().getId()));
            userCardInfoDtos.add(userCardInfoDto);
        }
        return userCardInfoDtos;
    }

    @Override
    public UserCardInfoDto updateQuantity(UserCardUpdate update) {
        UserCardEntity userCardEntity=this.userCardRepository.findById(update.getId())
                .orElseThrow(()->new EntityNotFoundException("Usted no posee la carta"));
        userCardEntity.setQuantity(update.getQuantity());
        userCardEntity=this.userCardRepository.save(userCardEntity);
        UserCardInfoDto userCardInfoDto=this.mapEntityToDtoUserCard(userCardEntity);
        userCardInfoDto.setCardInfoDto(this.cardService.getById(userCardEntity.getCard().getId()));
        return userCardInfoDto;
    }

    @Override
    public UserCardInfoDto processSellerCardSale (UserCardSaleDto userCardSaleDto) {
        UserCardEntity userCardEntity=this.userCardRepository.findByCard_IdAndAndUserId(userCardSaleDto.getCardId(),userCardSaleDto.getUserId())
                .orElseThrow(()->new EntityNotFoundException("El vendedor no tiene esa carta"));
        if(userCardEntity.getQuantity()<userCardSaleDto.getQuantity()){
            throw new IllegalArgumentException("La cantidad supera a la que tiene el vendedor");
        }
        userCardEntity.setQuantity(userCardEntity.getQuantity()-userCardSaleDto.getQuantity());
        userCardEntity=this.userCardRepository.save(userCardEntity);
        UserCardInfoDto userCardInfoDto=this.mapEntityToDtoUserCard(userCardEntity);
        userCardInfoDto.setCardInfoDto(this.cardService.getById(userCardEntity.getCard().getId()));
        return userCardInfoDto;
    }

    @Override
    public UserCardInfoDto processBuyingCardSale(UserCardSaleDto userCardSaleDto) {
        Optional<UserCardEntity> userCardEntity=this.userCardRepository.findByCard_IdAndAndUserId(userCardSaleDto.getCardId(),userCardSaleDto.getUserId());
        UserCardEntity userCard;
        if(userCardEntity.isPresent()){
            userCard=userCardEntity.get();
            userCard.setQuantity(userCard.getQuantity()+ userCardSaleDto.getQuantity());
            userCard=this.userCardRepository.save(userCard);
            return this.mapEntityToDtoUserCard(userCard);
        }
        else{
            CardInfoDto cardInfoDto=this.cardService.getById(userCardSaleDto.getCardId());
            userCard=new UserCardEntity();
            userCard.setQuantity(userCardSaleDto.getQuantity());
            userCard.setUserId(userCardSaleDto.getUserId());
            userCard.setCard(this.maptDtoToEntityCard(cardInfoDto));
            userCard=this.userCardRepository.save(userCard);
            return this.mapEntityToDtoUserCard(userCard);
        }

    }


    private CardEntity maptDtoToEntityCard(CardInfoDto cardInfoDto){
        CardEntity cardEntity=new CardEntity();
        cardEntity.setId(cardInfoDto.getId());
        cardEntity.setCode(cardInfoDto.getCode());
        cardEntity.setName(cardInfoDto.getName());
        cardEntity.setRarity(cardInfoDto.getRarity());
        cardEntity.setCreated_At(cardInfoDto.getCreated_At());
        cardEntity.setImage_url(cardInfoDto.getImage_url());
        cardEntity.setPrice(cardInfoDto.getPrice());
        cardEntity.setSuperType(cardInfoDto.getSuperType());
        return cardEntity;
    }
    private UserCardInfoDto mapEntityToDtoUserCard(UserCardEntity userCardEntity){
        UserCardInfoDto userCardInfoDto=new UserCardInfoDto();
        userCardInfoDto.setUserId(userCardEntity.getUserId());
        userCardInfoDto.setId(userCardEntity.getId());
        userCardInfoDto.setQuantity(userCardEntity.getQuantity());
        return userCardInfoDto;
    }
}
