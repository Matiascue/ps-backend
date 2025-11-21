package com.example.chat.service;

import com.example.chat.Dto.info.RoomInfoDto;
import com.example.chat.Dto.info.RoomsInfoDto;
import com.example.chat.Dto.post.RoomPostDto;

import java.util.List;

public interface RoomService {
    RoomInfoDto createOrGetRoom(RoomPostDto roomPostDto);
    List<RoomsInfoDto> getRooms(Long userId);
}
