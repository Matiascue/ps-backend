package com.example.sales_buy.client.card;

import com.example.sales_buy.client.user.UserClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CardClientService {
    private final WebClient webClient;
    public CardClientService(WebClient webClient){
        this.webClient=webClient;
    }

    public CardClient getCard(Long id){
        return webClient.get()
                .uri("http://cards-service:8081/api/card/getByid/"+id)
                .retrieve()
                .bodyToMono(CardClient.class)
                .block();
    }
}
