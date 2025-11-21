package com.example.cards.services.imp;

import com.example.cards.dto.CardInfoDto;
import com.example.cards.dto.UserCardInfoDto;
import com.example.cards.dto.UserCardSaleDto;
import com.example.cards.dto.post.CardPostDto;
import com.example.cards.dto.post.UserCardsPostDto;
import com.example.cards.dto.update.UserCardUpdate;
import com.example.cards.entity.CardEntity;
import com.example.cards.entity.UserCardEntity;
import com.example.cards.jwt.JwtUtil;
import com.example.cards.repository.UserCardRepository;
import com.example.cards.services.CardService;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserCardServiceImpTest {

    @InjectMocks
    private UserCardServiceImp userCardServiceImp;
    @Mock
    private UserCardRepository userCardRepository;
    @Mock
    private CardService cardService;
    @Mock
    private JwtUtil jwtUtil;

    private UserCardEntity userCardEntity;
    private CardEntity cardEntity;
    private CardPostDto cardPostDto;
    private UserCardsPostDto userCardsPostDto;
    private CardInfoDto cardInfoDto;
    @BeforeEach
    void setUp(){
        cardEntity=new CardEntity();
        cardEntity.setId(1L);
        cardEntity.setCode("jk-1");
        cardEntity.setName("Pikachu");
        cardEntity.setRarity("Holo");
        cardEntity.setCreated_At(LocalDate.now());
        cardEntity.setImage_url("http//:url-image");
        cardEntity.setPrice(BigDecimal.valueOf(100));
        cardEntity.setSuperType("GX");

        cardPostDto=new CardPostDto();
        cardPostDto.setCode("jk-1");
        cardPostDto.setName("Pikachu");
        cardPostDto.setRarity("Holo");
        cardPostDto.setPrice(BigDecimal.valueOf(100));
        cardPostDto.setSuperType("GX");
        cardPostDto.setImage_url("http//:url-image");

        userCardEntity=new UserCardEntity();
        userCardEntity.setQuantity(10);
        userCardEntity.setUserId(1L);
        userCardEntity.setId(1L);
        userCardEntity.setCard(cardEntity);

        userCardsPostDto=new UserCardsPostDto();
        userCardsPostDto.setCardPostDto(cardPostDto);
        userCardsPostDto.setUserId("1Lopei");
        userCardsPostDto.setQuantity(10);

        cardInfoDto=new CardInfoDto();
        cardInfoDto.setId(1L);
        cardInfoDto.setCode("jk-1");
        cardInfoDto.setName("Pikachu");
        cardInfoDto.setRarity("Holo");
        cardInfoDto.setCreated_At(LocalDate.now());
        cardInfoDto.setImage_url("http//:url-image");
        cardInfoDto.setPrice(BigDecimal.valueOf(100));
        cardInfoDto.setSuperType("GX");
    }

    @Test
    void registerUserCards() {
        List<UserCardsPostDto>userCardsPostDtoList=new ArrayList<>();
        userCardsPostDtoList.add(userCardsPostDto);
        Mockito.when(cardService.getByCode("jk-1")).thenReturn(cardInfoDto);
        Mockito.when(jwtUtil.extractUserId("1Lopei")).thenReturn(1L);
        Mockito.when(userCardRepository.findByCard_CodeAndUserId("jk-1",1L)).thenReturn(Optional.of(userCardEntity));
        Mockito.when(userCardRepository.save(Mockito.any(UserCardEntity.class))).thenReturn(userCardEntity);
        List<UserCardInfoDto>response=this.userCardServiceImp.registerUserCards(userCardsPostDtoList);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals(1L,response.get(0).getId());
        Assertions.assertEquals(1L,response.get(0).getUserId());
        Assertions.assertEquals(20,response.get(0).getQuantity());
    }

    @Test
    void registerUserCardsExist(){
        List<UserCardsPostDto>userCardsPostDtoList=new ArrayList<>();
        userCardsPostDtoList.add(userCardsPostDto);
        Mockito.when(cardService.getByCode("jk-1")).thenReturn(cardInfoDto);
        Mockito.when(jwtUtil.extractUserId("1Lopei")).thenReturn(1L);
        Mockito.when(userCardRepository.findByCard_CodeAndUserId("jk-1",1L)).thenReturn(Optional.empty());
        Mockito.when(userCardRepository.save(Mockito.any(UserCardEntity.class))).thenReturn(userCardEntity);
        List<UserCardInfoDto>response=this.userCardServiceImp.registerUserCards(userCardsPostDtoList);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals(1L,response.get(0).getId());
        Assertions.assertEquals(1L,response.get(0).getUserId());
        Assertions.assertEquals(10,response.get(0).getQuantity());
    }

    @Test
    void getAllByUserId() {
        List<UserCardEntity>userCardEntityList=new ArrayList<>();
        userCardEntityList.add(userCardEntity);
        Mockito.when(userCardRepository.findAllByUserId(1L)).thenReturn(userCardEntityList);
        Mockito.when(cardService.getById(1L)).thenReturn(cardInfoDto);
        List<UserCardInfoDto>response=this.userCardServiceImp.getAllByUserId(1L);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals(1L,response.get(0).getId());
        Assertions.assertEquals(1L,response.get(0).getUserId());
        Assertions.assertEquals(10,response.get(0).getQuantity());
    }

    @Test
    void updateQuantity() {
        userCardEntity.setQuantity(1);
        UserCardUpdate update=new UserCardUpdate();
        update.setQuantity(1);
        update.setUserId("1Lopwi");
        update.setId(1L);
        update.setCardId(1L);
        Mockito.when(userCardRepository.findById(1L)).thenReturn(Optional.of(userCardEntity));
        Mockito.when(userCardRepository.save(Mockito.any(UserCardEntity.class))).thenReturn(userCardEntity);
        Mockito.when(cardService.getById(1L)).thenReturn(cardInfoDto);
        UserCardInfoDto response=this.userCardServiceImp.updateQuantity(update);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L,response.getId());
        Assertions.assertEquals(1L,response.getUserId());
        Assertions.assertEquals(1,response.getQuantity());

    }

    @Test
    void updateQuantityNotFound(){
        UserCardUpdate update=new UserCardUpdate();
        update.setQuantity(1);
        update.setUserId("1Lopwi");
        update.setId(1L);
        Mockito.when(userCardRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException response=Assertions.assertThrows(EntityNotFoundException.class,()->{
            this.userCardServiceImp.updateQuantity(update);
        });
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Usted no posee la carta",response.getMessage());
    }
    @Test
    void processSellerCardSale(){
        UserCardSaleDto userCardSaleDto=new UserCardSaleDto();
        userCardSaleDto.setCardId(1L);
        userCardSaleDto.setQuantity(3);
        userCardSaleDto.setUserId(1L);
        Mockito.when(userCardRepository.findByCard_IdAndAndUserId(1L,1L)).thenReturn(Optional.of(userCardEntity));
        Mockito.when(userCardRepository.save(Mockito.any(UserCardEntity.class))).thenReturn(userCardEntity);
        Mockito.when(cardService.getById(1L)).thenReturn(cardInfoDto);
        UserCardInfoDto response=this.userCardServiceImp.processSellerCardSale(userCardSaleDto);
        Assertions.assertEquals(1L,response.getId());
        Assertions.assertEquals(1L,response.getUserId());
        Assertions.assertEquals(7,response.getQuantity());
    }
    @Test
    void processSellerCardSaleNotExist(){
        UserCardSaleDto userCardSaleDto=new UserCardSaleDto();
        userCardSaleDto.setCardId(1L);
        userCardSaleDto.setQuantity(3);
        userCardSaleDto.setUserId(1L);
        Mockito.when(userCardRepository.findByCard_IdAndAndUserId(1L,1L)).thenReturn(Optional.empty());
        EntityNotFoundException response=Assertions.assertThrows(EntityNotFoundException.class,()->{
            this.userCardServiceImp.processSellerCardSale(userCardSaleDto);
        });
        Assertions.assertNotNull(response);
        Assertions.assertEquals("El vendedor no tiene esa carta",response.getMessage());
    }
    @Test
    void processSellerCardSaleExceedQuantity(){
        UserCardSaleDto userCardSaleDto=new UserCardSaleDto();
        userCardSaleDto.setCardId(1L);
        userCardSaleDto.setQuantity(13);
        userCardSaleDto.setUserId(1L);
        Mockito.when(userCardRepository.findByCard_IdAndAndUserId(1L,1L)).thenReturn(Optional.of(userCardEntity));
        IllegalArgumentException response=Assertions.assertThrows(IllegalArgumentException.class,()->{
            this.userCardServiceImp.processSellerCardSale(userCardSaleDto);
        });
        Assertions.assertNotNull(response);
        Assertions.assertEquals("La cantidad supera a la que tiene el vendedor",response.getMessage());
    }
    @Test
    void processBuyingCardSale(){
        UserCardSaleDto userCardSaleDto=new UserCardSaleDto();
        userCardSaleDto.setCardId(1L);
        userCardSaleDto.setQuantity(3);
        userCardSaleDto.setUserId(1L);
        Mockito.when(userCardRepository.findByCard_IdAndAndUserId(1L,1L)).thenReturn(Optional.of(userCardEntity));
        Mockito.when(userCardRepository.save(Mockito.any(UserCardEntity.class))).thenReturn(userCardEntity);
        UserCardInfoDto response=this.userCardServiceImp.processBuyingCardSale(userCardSaleDto);
        Assertions.assertEquals(1L,response.getId());
        Assertions.assertEquals(1L,response.getUserId());
        Assertions.assertEquals(13,response.getQuantity());
    }
    @Test
    void processBuyingCardSaleNew(){
        UserCardSaleDto userCardSaleDto=new UserCardSaleDto();
        userCardSaleDto.setCardId(1L);
        userCardSaleDto.setQuantity(3);
        userCardSaleDto.setUserId(1L);
        Mockito.when(userCardRepository.findByCard_IdAndAndUserId(1L,1L)).thenReturn(Optional.empty());
        Mockito.when(userCardRepository.save(Mockito.any(UserCardEntity.class))).thenReturn(userCardEntity);
        Mockito.when(cardService.getById(1L)).thenReturn(cardInfoDto);
        UserCardInfoDto response=this.userCardServiceImp.processBuyingCardSale(userCardSaleDto);
        Assertions.assertEquals(1L,response.getId());
        Assertions.assertEquals(1L,response.getUserId());
        Assertions.assertEquals(10,response.getQuantity());
    }
}