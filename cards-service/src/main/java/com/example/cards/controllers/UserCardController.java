package com.example.cards.controllers;

import com.example.cards.dto.UserCardInfoDto;
import com.example.cards.dto.UserCardSaleDto;
import com.example.cards.dto.post.UserCardsPostDto;
import com.example.cards.dto.update.UserCardUpdate;
import com.example.cards.services.UserCardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user-card")
public class UserCardController {

    private final UserCardService userCardService;

    public UserCardController(UserCardService userCardService){
        this.userCardService=userCardService;
    }

    @GetMapping("/allCards/{userId}")
    public List<UserCardInfoDto> getAllByUser(@PathVariable Long userId){
        return this.userCardService.getAllByUserId(userId);
    }

    @PostMapping("/register-card")
    public List<UserCardInfoDto> registerUserCards(@RequestBody List<UserCardsPostDto>userCardsPostDtos){
        return this.userCardService.registerUserCards(userCardsPostDtos);
    }

    @PutMapping("/update")
    public UserCardInfoDto updateQuantity(@RequestBody UserCardUpdate update){
        return  this.userCardService.updateQuantity(update);
    }
    @PutMapping("/seller")
    public UserCardInfoDto updateQuantitySeller(@RequestBody UserCardSaleDto userCardSaleDto){
        return this.userCardService.processSellerCardSale(userCardSaleDto);
    }
    @PutMapping("buying")
    public UserCardInfoDto updateQuantityBuying(@RequestBody UserCardSaleDto userCardSaleDto){
        return this.userCardService.processBuyingCardSale(userCardSaleDto);
    }
}
