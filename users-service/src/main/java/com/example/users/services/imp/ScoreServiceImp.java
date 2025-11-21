package com.example.users.services.imp;

import com.example.users.dto.ScoreInfoDto;
import com.example.users.dto.post.ScorePostDto;
import com.example.users.entity.ScoreEntity;
import com.example.users.entity.UserEntity;
import com.example.users.repository.ScoreRepository;
import com.example.users.repository.UserRepository;
import com.example.users.services.ScoreService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreServiceImp implements ScoreService {

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;

    public ScoreServiceImp(ScoreRepository scoreRepository,UserRepository userRepository){
        this.scoreRepository=scoreRepository;
        this.userRepository=userRepository;
    }

    @Override
    public ScorePostDto sendScore(ScorePostDto scorePostDto) {
        UserEntity user=this.userRepository.findById(scorePostDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("El usuario no se encontro"));
        UserEntity userRated=this.userRepository.findById(scorePostDto.getUserRatedId())
                .orElseThrow(() -> new EntityNotFoundException("El usuario no se encontro"));
        ScoreEntity scoreEntity=new ScoreEntity();
        scoreEntity.setScore(scorePostDto.getScore());
        scoreEntity.setCommentary(scorePostDto.getCommentary());
        scoreEntity.setDate(LocalDateTime.now());
        scoreEntity.setUser(user);
        scoreEntity.setUserRated(userRated);
        this.scoreRepository.save(scoreEntity);
        return scorePostDto;
    }

    @Override
    public List<ScoreInfoDto> getScoreOfUser(Long userId) {
        List<ScoreEntity>scoreEntities=this.scoreRepository.findAllByUserRatedId(userId);
        List<ScoreInfoDto>scoreInfoDtos=new ArrayList<>();
        for(ScoreEntity sc:scoreEntities){
            ScoreInfoDto s=new ScoreInfoDto();
            s.setScore(sc.getScore());
            s.setUserId(sc.getUser().getId());
            s.setId(sc.getId());
            s.setDate(sc.getDate());
            s.setCommentary(sc.getCommentary());
            s.setUsername(sc.getUser().getUsername());
            s.setUserRatedId(sc.getUserRated().getId());
            scoreInfoDtos.add(s);
        }

        return scoreInfoDtos;
    }

    @Override
    public ScoreInfoDto updateScore(ScoreInfoDto scoreInfoDto) {
        ScoreEntity scoreEntity=this.scoreRepository.findById(scoreInfoDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("La puntuación no se encontro"));
        scoreEntity.setScore(scoreInfoDto.getScore());
        scoreEntity.setCommentary(scoreInfoDto.getCommentary());
        scoreEntity.setDate(LocalDateTime.now());
        scoreEntity=this.scoreRepository.save(scoreEntity);
        scoreInfoDto.setScore(scoreEntity.getScore());
        return scoreInfoDto;
    }

    @Override
    public double getAverage(Long userId) {
        double average=this.scoreRepository.findAverageScoredByUserRatedId(userId);
        return Math.min(average,5.0);
    }
}
