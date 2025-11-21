package com.example.users.dto.post;

import lombok.Data;

@Data
public class ScorePostDto {
    private Long userId;
    private Long userRatedId;
    private double score;
    private String commentary;
}
