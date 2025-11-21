package com.example.cards.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class ApiCardData {
    private String id;
    private String name;
    private String rarity;
    private String supertype;
    private Images images;

    @JsonProperty("tcgplayer")
    private Map<String, Object> tcgplayer;

    @Data
    public static class Images {
        private String small;
    }
}
