package com.example.chat.Dto.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomsInfoDto {
    private String roomId;
    private Long user1Id;
    private Long user2Id;
    private String lastMessage;
}
