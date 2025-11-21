package com.example.chat.Dto.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomInfoDto {
    private String roomId;
    private Long user1Id;
    private Long user2Id;
    private List<ChatInfoDto>messagesUser1;
    private List<ChatInfoDto>messagesUser2;
}
