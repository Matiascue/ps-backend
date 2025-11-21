package com.example.users.services;

import com.example.users.dto.ScoreInfoDto;
import com.example.users.dto.post.ScorePostDto;

import java.util.List;

public interface ScoreService {
    ScorePostDto sendScore(ScorePostDto scorePostDto);
    List<ScoreInfoDto>getScoreOfUser(Long userId);
    ScoreInfoDto updateScore(ScoreInfoDto scoreInfoDto);
    double getAverage(Long userId);
}
