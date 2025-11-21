package com.example.sales_buy.client.userCard;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserCardClientService {
    private final WebClient webClient;
    public UserCardClientService(WebClient webClient){
        this.webClient=webClient;
    }

    public UserCardClient updateSellerQuantity(Long userId, int quantity, Long cardId){
        UserCardClient u=new UserCardClient();
        u.setQuantity(quantity);
        u.setUserId(userId);
        u.setCardId(cardId);
        return webClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/seller")
                        .build())
                .bodyValue(u)
                .retrieve()
                .bodyToMono(UserCardClient.class)
                .block();
    }
    public UserCardClient updateBuyerQuantity(Long userId,int quantity,Long cardId){
        UserCardClient u=new UserCardClient();
        u.setQuantity(quantity);
        u.setUserId(userId);
        u.setCardId(cardId);
        return webClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/buying")
                        .build())
                .bodyValue(u)
                .retrieve()
                .bodyToMono(UserCardClient.class)
                .block();
    }
}
