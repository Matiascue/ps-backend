package com.example.cards.dto.update;

import com.example.cards.dto.CardInfoDto;
import lombok.Data;

@Data
public class UserCardUpdate {
    private Long id;
    private String userId;
    private Long cardId;
    private int quantity;
}
