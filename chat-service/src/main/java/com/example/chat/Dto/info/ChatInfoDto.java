package com.example.chat.Dto.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatInfoDto {
    private Long id;
    private String roomId;
    private Long senderId;
    private String content;
    private LocalDateTime timeStamp;
}
