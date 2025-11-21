package com.example.cards.client;

import lombok.Data;

import java.util.List;

@Data
public class ApiCardResponse {
    private List<ApiCardData> data;
}
