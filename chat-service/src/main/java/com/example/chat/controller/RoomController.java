package com.example.chat.controller;

import com.example.chat.Dto.info.ChatInfoDto;
import com.example.chat.Dto.info.RoomInfoDto;
import com.example.chat.Dto.info.RoomsInfoDto;
import com.example.chat.Dto.post.RoomPostDto;
import com.example.chat.service.ChatService;
import com.example.chat.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/room")
@CrossOrigin("*")
public class RoomController {

    private final RoomService roomService;
    private final ChatService chatService;

    public RoomController(RoomService roomService,ChatService chatService){
        this.roomService=roomService;
        this.chatService=chatService;
    }

    @PostMapping("/create")
    public RoomInfoDto createOrGetRoom(@RequestBody RoomPostDto roomPostDto){
        return this.roomService.createOrGetRoom(roomPostDto);
    }
    @GetMapping("/{roomId}/messages")
    public List<ChatInfoDto> getHistorial(@PathVariable String roomId){
        return this.chatService.getAllMessages(roomId);
    }
    @GetMapping("/rooms/{userId}")
    public List<RoomsInfoDto>getRooms(@PathVariable Long userId){
        return this.roomService.getRooms(userId);
    }
}
