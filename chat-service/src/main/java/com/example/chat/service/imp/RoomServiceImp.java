package com.example.chat.service.imp;

import com.example.chat.Dto.info.ChatInfoDto;
import com.example.chat.Dto.info.RoomInfoDto;
import com.example.chat.Dto.info.RoomsInfoDto;
import com.example.chat.Dto.post.RoomPostDto;
import com.example.chat.Repository.RoomRepository;
import com.example.chat.entity.RoomEntity;
import com.example.chat.service.ChatService;
import com.example.chat.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImp implements RoomService {

    private final RoomRepository roomRepository;
    private final ChatService chatService;

    public RoomServiceImp(RoomRepository roomRepository,ChatService chatService){
        this.roomRepository=roomRepository;
        this.chatService=chatService;
    }

    @Override
    public RoomInfoDto createOrGetRoom(RoomPostDto roomPostDto) {
        Optional<RoomEntity>roomExist=this.roomRepository.findById(generateRoomId(roomPostDto.getUser1Id(),roomPostDto.getUser2Id()));
        RoomInfoDto roomInfoDto=new RoomInfoDto();
        if(roomExist.isPresent()){
            RoomEntity roomEntity=roomExist.get();
            roomInfoDto.setRoomId(roomEntity.getId());
            roomInfoDto.setUser1Id(roomEntity.getUser1Id());
            roomInfoDto.setUser2Id(roomEntity.getUser2Id());
            roomInfoDto.setMessagesUser1(this.getMessagesByUser(roomEntity.getUser1Id(),roomEntity.getId()));
            roomInfoDto.setMessagesUser2(this.getMessagesByUser(roomEntity.getUser2Id(),roomEntity.getId()));
        }
        else{
            RoomEntity roomEntity=new RoomEntity();
            roomEntity.setId(generateRoomId(roomPostDto.getUser1Id(),roomPostDto.getUser2Id()));
            roomEntity.setUser1Id(roomPostDto.getUser1Id());
            roomEntity.setUser2Id(roomPostDto.getUser2Id());
            roomEntity=this.roomRepository.save(roomEntity);
            roomInfoDto.setRoomId(roomEntity.getId());
            roomInfoDto.setUser1Id(roomEntity.getUser1Id());
            roomInfoDto.setUser2Id(roomEntity.getUser2Id());
        }
        return roomInfoDto;
    }

    @Override
    public List<RoomsInfoDto> getRooms(Long userId) {
        List<RoomEntity>roomEntityList=this.roomRepository.getAllByUserId(userId);
        return roomEntityList.stream().map(this::mapEntityToRoomsInfoDto).toList();
    }

    private RoomsInfoDto mapEntityToRoomsInfoDto(RoomEntity roomEntity){
        RoomsInfoDto roomsInfoDto=new RoomsInfoDto();
        roomsInfoDto.setRoomId(roomEntity.getId());
        roomsInfoDto.setUser1Id(roomEntity.getUser1Id());
        roomsInfoDto.setUser2Id(roomEntity.getUser2Id());
        roomsInfoDto.setLastMessage(this.getLastMessage(roomEntity.getId()));
        return roomsInfoDto;
    }

    private String getLastMessage(String roomId){
        List<ChatInfoDto>chatInfoDtoList=this.chatService.getAllMessages(roomId);
        if(!chatInfoDtoList.isEmpty()){
            return chatInfoDtoList.get(chatInfoDtoList.size()-1).getContent();
        }
        return "";
    }

    private List<ChatInfoDto>getMessagesByUser(Long userId,String roomId){
        List<ChatInfoDto>chatInfoDtoList=this.chatService.getAllMessages(roomId);
        return chatInfoDtoList.stream().filter(chatInfoDto -> chatInfoDto.getSenderId().equals(userId))
                .toList();
    }
    private String generateRoomId(Long userA, Long userB) {
        return (userA < userB)
                ? userA + "_" + userB
                : userB + "_" + userA;
    }
}
