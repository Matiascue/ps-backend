package com.example.cards.services.imp;

import com.example.cards.client.ApiCardData;
import com.example.cards.client.PokemonApiClientService;
import com.example.cards.dto.CardInfoDto;
import com.example.cards.dto.post.CardPostDto;
import com.example.cards.entity.CardEntity;
import com.example.cards.repository.CardRepository;
import com.example.cards.services.CardService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CardServiceImp implements CardService {

    private final CardRepository cardRepository;
    private final PokemonApiClientService pokemonApiClientService;
    public CardServiceImp(CardRepository cardRepository,PokemonApiClientService pokemonApiClientService){
        this.cardRepository=cardRepository;
        this.pokemonApiClientService=pokemonApiClientService;
    }

    @Override
    public CardInfoDto createCard(CardPostDto cardPostDto) {
        Optional<CardEntity>cardExist=cardRepository.findByCode(cardPostDto.getCode());
        if(cardExist.isPresent()){
            throw new RuntimeException("La carta ya existe");
        }
        CardEntity cardEntity=new CardEntity();
        cardEntity.setCode(cardPostDto.getCode());
        cardEntity.setName(cardPostDto.getName());
        cardEntity.setRarity(cardPostDto.getRarity());
        cardEntity.setCreated_At(LocalDate.now());
        cardEntity.setImage_url(cardPostDto.getImage_url());
        cardEntity.setPrice(cardPostDto.getPrice());
        cardEntity.setSuperType(cardPostDto.getSuperType());
        cardEntity=this.cardRepository.save(cardEntity);
        return this.mapEntityToDto(cardEntity);
    }

    @Override
    public CardInfoDto getByCode(String code) {
        CardEntity cardEntity=cardRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("La carta no se encontro"));
        return this.mapEntityToDto(cardEntity);
    }

    @Override
    public CardInfoDto getById(Long id) {
        CardEntity cardEntity=cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La carta no se encontro"));
        return this.mapEntityToDto(cardEntity);
    }

    @Override
    public Page<CardInfoDto> getAll(Pageable pageable) {
        Page<CardEntity> cardEntities = cardRepository.findAll(pageable);
        return cardEntities.map(this::mapEntityToDto);
    }

    @Scheduled(cron = "0 0 13  * * ?") // Todos los días a las 13PM
    public void syncCardsFromApi() {
        List<Map<String, Object>> sets = pokemonApiClientService.fetchAllSets();

        for (Map<String, Object> set : sets) {
            String setId = (String) set.get("id");
            int page = 1;
            while (true) {
                List<ApiCardData> cards = pokemonApiClientService.fetchCardsBySetId(setId, page++);
                if (cards == null || cards.isEmpty()) break;

                for (ApiCardData apiCard : cards) {
                    if (cardRepository.findByCode(apiCard.getId()).isEmpty()) {
                        CardEntity card = new CardEntity();
                        card.setCode(apiCard.getId());
                        card.setName(apiCard.getName());
                        card.setRarity(apiCard.getRarity());
                        card.setSuperType(apiCard.getSupertype());
                        card.setImage_url(apiCard.getImages().getSmall());
                        card.setCreated_At(LocalDate.now());
                        card.setPrice(extractPrice(apiCard));
                        cardRepository.save(card);
                    }
                }
            }
        }
    }
    private BigDecimal extractPrice(ApiCardData card) {
        try {
            Map<String, Object> tcg = (Map<String, Object>) card.getTcgplayer().get("prices");
            for (String key : List.of("normal", "holofoil", "reverseHolofoil", "1stEditionHolofoil",
                    "unlimitedHolofoil")) {
                if (tcg.containsKey(key)) {
                    Map<String, Object> info = (Map<String, Object>) tcg.get(key);
                    if (info.containsKey("market")) {
                        Object priceValue = info.get("market");
                        if (priceValue instanceof Number) {
                            return BigDecimal.valueOf(((Number) priceValue).doubleValue());
                        }
                    }
                }
            }
        } catch (Exception ignored) {}
        return BigDecimal.ZERO;
    }
        private CardInfoDto mapEntityToDto(CardEntity cardEntity){
            CardInfoDto cardInfoDto=new CardInfoDto();
            cardInfoDto.setId(cardEntity.getId());
            cardInfoDto.setCode(cardEntity.getCode());
            cardInfoDto.setName(cardEntity.getName());
            cardInfoDto.setPrice(cardEntity.getPrice());
            cardInfoDto.setRarity(cardEntity.getRarity());
            cardInfoDto.setImage_url(cardEntity.getImage_url());
            cardInfoDto.setSuperType(cardEntity.getSuperType());
            cardInfoDto.setCreated_At(cardEntity.getCreated_At());
            return cardInfoDto;
        }
}
