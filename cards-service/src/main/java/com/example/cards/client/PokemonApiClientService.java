package com.example.cards.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class PokemonApiClientService {
    private final WebClient webClient;

    public PokemonApiClientService(WebClient webClient) {
        this.webClient = webClient;
    }
    public List<Map<String, Object>> fetchAllSets() {
        return webClient.get()
                .uri("/sets")
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (List<Map<String, Object>>) response.get("data"))
                .block();
    }

    public List<ApiCardData> fetchCardsBySetId(String setId, int page) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cards")
                        .queryParam("q", "set.id:" + setId)
                        .queryParam("page", page)
                        .queryParam("pageSize", 200)
                        .build())
                .retrieve()
                .bodyToMono(ApiCardResponse.class)
                .map(ApiCardResponse::getData)
                .block();
    }
}
