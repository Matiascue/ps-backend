package com.example.cards.services;

import com.example.cards.dto.CardInfoDto;
import com.example.cards.dto.post.CardPostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CardService {
    CardInfoDto createCard(CardPostDto cardPostDto);
    CardInfoDto getByCode(String code);
    CardInfoDto getById(Long id);
    Page<CardInfoDto> getAll(Pageable pageable);
}
