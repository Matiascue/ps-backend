package com.example.cards.services;

import com.example.cards.dto.UserCardInfoDto;
import com.example.cards.dto.UserCardSaleDto;
import com.example.cards.dto.post.UserCardsPostDto;
import com.example.cards.dto.update.UserCardUpdate;

import java.util.List;

public interface UserCardService {
    List<UserCardInfoDto> registerUserCards(List<UserCardsPostDto> userCardsPostDtos);
    List<UserCardInfoDto> getAllByUserId(Long userId);
    UserCardInfoDto updateQuantity(UserCardUpdate update);
    UserCardInfoDto processSellerCardSale (UserCardSaleDto userCardSaleDto);
    UserCardInfoDto processBuyingCardSale(UserCardSaleDto userCardSaleDto);
}
