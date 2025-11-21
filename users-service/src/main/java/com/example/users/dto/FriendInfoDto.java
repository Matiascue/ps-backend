package com.example.users.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FriendInfoDto {
    private Long id;
    private Long userId;
    private Long friendId;
    private String friendUsername;
    private LocalDateTime requestDate;
}
