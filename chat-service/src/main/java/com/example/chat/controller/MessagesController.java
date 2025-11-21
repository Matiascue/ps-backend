package com.example.chat.controller;

import com.example.chat.Dto.info.ChatInfoDto;
import com.example.chat.Dto.post.ChatPostDto;
import com.example.chat.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(originPatterns = "*",allowCredentials = "true")
public class MessagesController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    public MessagesController(ChatService chatService,SimpMessagingTemplate simpMessagingTemplate){
        this.messagingTemplate=simpMessagingTemplate;
        this.chatService=chatService;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatPostDto chatPostDto){
        ChatInfoDto chatInfoDto=this.chatService.sendMessage(chatPostDto);
        messagingTemplate.convertAndSend(
                "/topic/chat"+chatPostDto.getRoomId(),
                chatInfoDto
        );
    }

}
