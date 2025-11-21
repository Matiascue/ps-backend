package com.example.chat.service;

import com.example.chat.Dto.info.ChatInfoDto;
import com.example.chat.Dto.post.ChatPostDto;

import java.util.List;

public interface ChatService {
    ChatInfoDto sendMessage(ChatPostDto chatPostDto);
    List<ChatInfoDto> getAllMessages(String roomId);
}
