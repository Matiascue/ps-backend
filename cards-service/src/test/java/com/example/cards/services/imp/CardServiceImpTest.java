package com.example.cards.services.imp;

import com.example.cards.client.ApiCardData;
import com.example.cards.client.PokemonApiClientService;
import com.example.cards.dto.CardInfoDto;
import com.example.cards.dto.post.CardPostDto;
import com.example.cards.entity.CardEntity;
import com.example.cards.repository.CardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CardServiceImpTest {

    @InjectMocks
    private CardServiceImp cardServiceImp;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private PokemonApiClientService pokemonApiClientService;

    private CardEntity cardEntity;
    private CardPostDto cardPostDto;
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
    }

    @Test
    void createCard() {
        Mockito.when(cardRepository.findByCode("jk-1")).thenReturn(Optional.empty());
        Mockito.when(cardRepository.save(Mockito.any(CardEntity.class))).thenReturn(cardEntity);
        CardInfoDto response=this.cardServiceImp.createCard(cardPostDto);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L,response.getId());
        Assertions.assertEquals("jk-1",response.getCode());
        Assertions.assertEquals("Pikachu",response.getName());
    }

    @Test
    void createCardExist(){
        Mockito.when(cardRepository.findByCode("jk-1")).thenReturn(Optional.of(cardEntity));
        RuntimeException response=Assertions.assertThrows(RuntimeException.class,()->{
            this.cardServiceImp.createCard(cardPostDto);
        });
        Assertions.assertNotNull(response);
        Assertions.assertEquals("La carta ya existe",response.getMessage());
    }

    @Test
    void getByCode() {
        Mockito.when(cardRepository.findByCode("jk-1")).thenReturn(Optional.of(cardEntity));
        CardInfoDto response=this.cardServiceImp.getByCode("jk-1");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L,response.getId());
        Assertions.assertEquals("jk-1",response.getCode());
        Assertions.assertEquals("Pikachu",response.getName());
    }

    @Test
    void getByCodeNotFound(){
        Mockito.when(cardRepository.findByCode("jk-1")).thenReturn(Optional.empty());
        EntityNotFoundException response=Assertions.assertThrows(EntityNotFoundException.class,()->{
            this.cardServiceImp.getByCode("jk-1");
        });
        Assertions.assertNotNull(response);
        Assertions.assertEquals("La carta no se encontro",response.getMessage());
    }

    @Test
    void getById() {
        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(cardEntity));
        CardInfoDto response=this.cardServiceImp.getById(1L);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L,response.getId());
        Assertions.assertEquals("jk-1",response.getCode());
        Assertions.assertEquals("Pikachu",response.getName());
    }

    @Test
    void getByIdNotFound(){
        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException response=Assertions.assertThrows(EntityNotFoundException.class,()->{
            this.cardServiceImp.getById(1L);
        });
        Assertions.assertNotNull(response);
        Assertions.assertEquals("La carta no se encontro",response.getMessage());
    }
    @Test
    void getAllWithPageable() {
        Pageable pageable = PageRequest.of(0, 2);
        List<CardEntity> cardEntities = List.of(cardEntity, cardEntity); //
        Page<CardEntity> cardEntityPage = new PageImpl<>(cardEntities, pageable, cardEntities.size());

        Mockito.when(cardRepository.findAll(pageable)).thenReturn(cardEntityPage);

        Page<CardInfoDto> result = cardServiceImp.getAll(pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getContent().size());
        Assertions.assertEquals("jk-1", result.getContent().get(0).getCode());
    }
    @Test
    void syncCardsFromApi_shouldSaveNewCards() {
        Map<String, Object> set1 = new HashMap<>();
        set1.put("id", "base1");
        Mockito.when(pokemonApiClientService.fetchAllSets())
                .thenReturn(List.of(set1));
        ApiCardData apiCard = new ApiCardData();
        apiCard.setId("abc-123");
        apiCard.setName("Bulbasaur");
        apiCard.setRarity("Common");
        apiCard.setSupertype("Pokémon");

        ApiCardData.Images images = new ApiCardData.Images();
        images.setSmall("http://image-url.com");
        apiCard.setImages(images);

        Map<String, Object> prices = new HashMap<>();
        Map<String, Object> normal = new HashMap<>();
        normal.put("market", 1.5);
        prices.put("normal", normal);

        Map<String, Object> tcgplayer = new HashMap<>();
        tcgplayer.put("prices", prices);
        apiCard.setTcgplayer(tcgplayer);

        Mockito.when(pokemonApiClientService.fetchCardsBySetId("base1", 1))
                .thenReturn(List.of(apiCard));
        Mockito.when(pokemonApiClientService.fetchCardsBySetId("base1", 2))
                .thenReturn(Collections.emptyList());

        Mockito.when(cardRepository.findByCode("abc-123"))
                .thenReturn(Optional.empty());

        Mockito.when(cardRepository.save(Mockito.any(CardEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        cardServiceImp.syncCardsFromApi();
        Mockito.verify(cardRepository).save(Mockito.argThat(card ->
                card.getCode().equals("abc-123") &&
                        card.getName().equals("Bulbasaur") &&
                        card.getPrice().compareTo(BigDecimal.valueOf(1.5)) == 0
        ));
    }
}