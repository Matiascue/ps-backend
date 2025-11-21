package com.example.users.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserScoreDto {
    private String username;
    private double averageScore;
    private long totalRatings;
}
