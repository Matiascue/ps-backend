package com.example.users.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScoreInfoDto {
    private Long id;
    private Long userId;
    private String username;
    private Long userRatedId;
    private double score;
    private String commentary;
    private LocalDateTime date;
}
