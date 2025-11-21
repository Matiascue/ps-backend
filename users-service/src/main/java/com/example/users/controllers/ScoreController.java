package com.example.users.controllers;

import com.example.users.dto.ScoreInfoDto;
import com.example.users.dto.dashboard.UserScoreDto;
import com.example.users.dto.post.ScorePostDto;
import com.example.users.services.ScoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/score")
@CrossOrigin("*")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService){
        this.scoreService=scoreService;
    }

    @PostMapping("/rate")
    public ScorePostDto rate(@RequestBody ScorePostDto scorePostDto){
        return this.scoreService.sendScore(scorePostDto);
    }

    @PutMapping("/update")
    public ScoreInfoDto upateRate(@RequestBody ScoreInfoDto scoreInfoDto){
        return this.scoreService.updateScore(scoreInfoDto);
    }

    @GetMapping("/{userId}")
    public List<ScoreInfoDto> getAllScoreByUserId(@PathVariable Long userId){
     return this.scoreService.getScoreOfUser(userId);
    }
    @GetMapping("/score/{userId}")
    public double getScore(@PathVariable Long userId){
        return this.scoreService.getAverage(userId);
    }

}
