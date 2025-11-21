package com.example.chat.Dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatPostDto {
    private String roomId;
    private Long senderId;
    private String content;
    private LocalDateTime timeStamp;
}
