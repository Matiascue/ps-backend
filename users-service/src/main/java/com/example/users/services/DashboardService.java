package com.example.users.services;

import com.example.users.dto.dashboard.UserScoreDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface DashboardService {
    List<UserScoreDto> findTopRatedUsers(boolean active, String role, LocalDateTime fromDate,LocalDateTime toDate, Pageable pageable);
    List<UserScoreDto> findWorstRatedUsers(boolean active, String role, LocalDateTime fromDate,LocalDateTime toDate, Pageable pageable);

}
