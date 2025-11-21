package com.example.users.controllers;

import com.example.users.dto.dashboard.UserScoreDto;
import com.example.users.services.DashboardService;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService){
        this.dashboardService=dashboardService;
    }

    @GetMapping("/top-scores")
    public List<UserScoreDto> getTopRatedUsers(@RequestParam boolean active, @RequestParam String rol,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate){
        return this.dashboardService.findTopRatedUsers(active,rol,fromDate,toDate,PageRequest.of(0,10));
    }
    @GetMapping("/worst-scores")
    public List<UserScoreDto> getWorstRatedUsers(@RequestParam boolean active, @RequestParam String rol,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate){
        return this.dashboardService.findWorstRatedUsers(active,rol,fromDate,toDate,PageRequest.of(0,10));
    }
}
