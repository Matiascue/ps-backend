package com.example.users.dto.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FriendPostDto {
    private Long userId;
    private Long friendId;
    private LocalDateTime requestDate;
}
