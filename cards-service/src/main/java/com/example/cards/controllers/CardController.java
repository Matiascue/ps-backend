package com.example.cards.controllers;

import com.example.cards.dto.CardInfoDto;
import com.example.cards.services.CardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService){
        this.cardService=cardService;
    }

    @GetMapping("/getByid/{id}")
    public CardInfoDto getById(@PathVariable Long id){
        return this.cardService.getById(id);
    }
    @GetMapping
    public Page<CardInfoDto>getCards(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "20")int size){
        Pageable pageable = PageRequest.of(page, size);
        return cardService.getAll(pageable);
    }
}
