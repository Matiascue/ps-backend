package com.example.chat.service.imp;

import com.example.chat.Dto.info.ChatInfoDto;
import com.example.chat.Dto.info.RoomInfoDto;
import com.example.chat.Dto.post.RoomPostDto;
import com.example.chat.Repository.RoomRepository;
import com.example.chat.entity.RoomEntity;
import com.example.chat.service.ChatService;
import org.junit.jupiter.api.Assertions;
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
class RoomServiceImpTest {

    @InjectMocks
    private RoomServiceImp roomServiceImp;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private ChatService chatService;

    private RoomEntity roomEntity;
    private ChatInfoDto chatInfoDto1;
    private ChatInfoDto chatInfoDto2;
    private LocalDateTime time=LocalDateTime.of(2023, 1, 1, 12, 0, 0);

    @BeforeEach
    void setUp(){
    roomEntity=new RoomEntity();
    roomEntity.setId("1_2");
    roomEntity.setUser1Id(1L);
    roomEntity.setUser2Id(2L);

    chatInfoDto1=new ChatInfoDto();
    chatInfoDto1.setRoomId("1_2");
    chatInfoDto1.setContent("Hola realizas envios?");
    chatInfoDto1.setTimeStamp(time);
    chatInfoDto1.setSenderId(1L);
    chatInfoDto1.setId(1L);

    chatInfoDto2=new ChatInfoDto();
    chatInfoDto2.setRoomId("1_2");
    chatInfoDto2.setContent("Hola, si hago envios");
    chatInfoDto2.setTimeStamp(time);
    chatInfoDto2.setSenderId(2L);
    chatInfoDto2.setId(2L);
    }

    @Test
    void createOrGetRoomCreateSuccess() {
        Mockito.when(roomRepository.findById("1_2")).thenReturn(Optional.empty());
        Mockito.when(roomRepository.save(Mockito.any(RoomEntity.class))).thenReturn(roomEntity);

        RoomPostDto roomPostDto=new RoomPostDto();
        roomPostDto.setUser1Id(1L);
        roomPostDto.setUser2Id(2L);

        RoomInfoDto response=this.roomServiceImp.createOrGetRoom(roomPostDto);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("1_2",response.getRoomId());
        Assertions.assertEquals(1L,response.getUser1Id());
        Assertions.assertEquals(2L,response.getUser2Id());
    }
    @Test
    void createOrGetRoomGetExists() {
        List<ChatInfoDto>chatInfoDtos1=new ArrayList<>();
        chatInfoDtos1.add(chatInfoDto1);
        chatInfoDtos1.add(chatInfoDto2);

        Mockito.when(roomRepository.findById("1_2")).thenReturn(Optional.of(roomEntity));
        Mockito.when(chatService.getAllMessages("1_2")).thenReturn(chatInfoDtos1);
        RoomPostDto roomPostDto=new RoomPostDto();
        roomPostDto.setUser1Id(1L);
        roomPostDto.setUser2Id(2L);

        RoomInfoDto response=this.roomServiceImp.createOrGetRoom(roomPostDto);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("1_2",response.getRoomId());
        Assertions.assertEquals(1L,response.getUser1Id());
        Assertions.assertEquals(2L,response.getUser2Id());
        Assertions.assertEquals(1,response.getMessagesUser1().size());
        Assertions.assertEquals(1,response.getMessagesUser2().size());
        Assertions.assertEquals("Hola realizas envios?",response.getMessagesUser1().get(0).getContent());
        Assertions.assertEquals("Hola, si hago envios",response.getMessagesUser2().get(0).getContent());
    }
}