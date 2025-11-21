package com.example.users.services.imp;

import com.example.users.dto.dashboard.UserScoreDto;
import com.example.users.repository.ScoreRepository;
import com.example.users.services.DashboardService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardServiceImp implements DashboardService {

    private final ScoreRepository scoreRepository;

    public DashboardServiceImp(ScoreRepository scoreRepository){
        this.scoreRepository=scoreRepository;
    }

    @Override
    public List<UserScoreDto>  findTopRatedUsers(boolean active, String role, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable) {
        return scoreRepository.findTopRatedUsers(active,role,fromDate,toDate,pageable);
    }

    @Override
    public List<UserScoreDto> findWorstRatedUsers(boolean active, String role, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable) {
        return scoreRepository.findWorstRatedUsers(active,role,fromDate,toDate,pageable);
    }
}
