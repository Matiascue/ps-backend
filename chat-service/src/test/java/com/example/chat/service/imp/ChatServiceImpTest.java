package com.example.chat.service.imp;

import com.example.chat.Dto.info.ChatInfoDto;
import com.example.chat.Dto.post.ChatPostDto;
import com.example.chat.Repository.ChatRepository;
import com.example.chat.Repository.RoomRepository;
import com.example.chat.entity.ChatEntity;
import com.example.chat.entity.RoomEntity;
import com.example.chat.service.ChatService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ChatServiceImpTest {

    @InjectMocks
    private ChatServiceImp chatServiceImp;
    @Mock
    private ChatRepository chatRepository;
    @Mock
    private RoomRepository roomRepository;

    private RoomEntity roomEntity;
    private ChatEntity chatEntity;

    @BeforeEach
    void setUp(){
        roomEntity=new RoomEntity();
        roomEntity.setId("1_2");
        roomEntity.setUser1Id(1L);
        roomEntity.setUser2Id(2L);

        chatEntity=new ChatEntity();
        chatEntity.setRoomId(roomEntity);
        chatEntity.setContent("Hola haces envios?");
        chatEntity.setTimeStamp(LocalDateTime.of(2023, 1, 1, 12, 0, 0));
        chatEntity.setId(1L);
        chatEntity.setSenderId(1L);
    }

    @Test
    void sendMessage() {
        Mockito.when(roomRepository.findById("1_2")).thenReturn(Optional.of(roomEntity));
        Mockito.when(chatRepository.save(Mockito.any(ChatEntity.class))).thenReturn(chatEntity);

        ChatPostDto chatPostDto = new ChatPostDto();
        chatPostDto.setContent("Hola haces envios?");
        chatPostDto.setRoomId("1_2");
        chatPostDto.setSenderId(1L);
        chatPostDto.setTimeStamp(LocalDateTime.of(2023, 1, 1, 12, 0, 0));

        ChatInfoDto response = this.chatServiceImp.sendMessage(chatPostDto);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L,response.getId());
        Assertions.assertEquals("Hola haces envios?",response.getContent());
        Assertions.assertEquals("1_2",response.getRoomId());
        Assertions.assertEquals(1L,response.getSenderId());
    }

    @Test
    void getAllMessages() {
        List<ChatEntity>chatEntities=new ArrayList<>();
        chatEntities.add(chatEntity);

        Mockito.when(chatRepository.findAllByRoomIdId("1_2")).thenReturn(chatEntities);

        List<ChatInfoDto>response=this.chatServiceImp.getAllMessages("1_2");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals(1L,response.get(0).getId());
        Assertions.assertEquals("Hola haces envios?",response.get(0).getContent());
        Assertions.assertEquals("1_2",response.get(0).getRoomId());
        Assertions.assertEquals(1L,response.get(0).getSenderId());
    }
}