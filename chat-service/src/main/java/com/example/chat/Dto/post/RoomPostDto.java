package com.example.chat.Dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomPostDto {
    private Long user1Id;
    private Long user2Id;
}
