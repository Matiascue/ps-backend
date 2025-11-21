package com.example.chat.service.imp;

import com.example.chat.Dto.info.ChatInfoDto;
import com.example.chat.Dto.post.ChatPostDto;
import com.example.chat.Repository.ChatRepository;
import com.example.chat.Repository.RoomRepository;
import com.example.chat.entity.ChatEntity;
import com.example.chat.entity.RoomEntity;
import com.example.chat.service.ChatService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImp implements ChatService {

    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;

    public ChatServiceImp(RoomRepository roomRepository,ChatRepository chatRepository){
        this.chatRepository=chatRepository;
        this.roomRepository=roomRepository;
    }

    @Override
    public ChatInfoDto sendMessage(ChatPostDto chatPostDto) {
        ChatEntity chatEntity=new ChatEntity();
        chatEntity.setContent(chatPostDto.getContent());
        chatEntity.setSenderId(chatPostDto.getSenderId());
        chatEntity.setTimeStamp(chatPostDto.getTimeStamp());
        chatEntity.setRoomId(this.getRoomEntity(chatPostDto.getRoomId()));
        chatEntity=this.chatRepository.save(chatEntity);
        return this.mapChatEntityToDto(chatEntity);
    }
    @Override
    public List<ChatInfoDto> getAllMessages(String roomId) {
        List<ChatEntity>chatEntities=this.chatRepository.findAllByRoomIdId(roomId);
        return chatEntities.stream().map(this::mapChatEntityToDto).toList();
    }

    private RoomEntity getRoomEntity(String id){
       Optional<RoomEntity> roomEntity = this.roomRepository.findById(id);
        return roomEntity.orElseThrow(()-> new EntityNotFoundException("RoomEntity con id " + id + " no encontrada"));
    }
    private ChatInfoDto mapChatEntityToDto(ChatEntity chat){
        ChatInfoDto chatInfoDto=new ChatInfoDto();
        chatInfoDto.setId(chat.getId());
        chatInfoDto.setContent(chat.getContent());
        chatInfoDto.setRoomId(chat.getRoomId().getId());
        chatInfoDto.setSenderId(chat.getSenderId());
        chatInfoDto.setTimeStamp(chat.getTimeStamp());
        return chatInfoDto;
    }
}
