package com.example.users.controllers;

import com.example.users.dto.FriendInfoDto;
import com.example.users.dto.post.FriendPostDto;
import com.example.users.services.FriendService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friend")
@CrossOrigin("*")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService){
        this.friendService=friendService;
    }

    @PostMapping("/send")
    public FriendPostDto sendRequest(@RequestBody FriendPostDto friendPostDto){
        return this.friendService.sendFriendRequest(friendPostDto);
    }
    @PostMapping("/listing/{userId}")
    public void sendListingMail(@PathVariable Long userId){
        this.friendService.sendListingEmail(userId);
    }
    @PatchMapping("/accept/{requestId}")
    public void acceptRequest(@PathVariable Long requestId){
        this.friendService.acceptFriendRequest(requestId);
    }
    @PatchMapping("/cancel/{requestId}")
    public void cancelRequest(@PathVariable Long requestId){
        this.friendService.cancelFriendRequest(requestId);
    }
    @GetMapping("/myFriends/{userId}")
    public List<FriendInfoDto>getAllMyFriends(@PathVariable Long userId){
        return this.friendService.getAllFriendsByUserId(userId);
    }
    @GetMapping("/request/{userId}")
    public List<FriendInfoDto>getAllRequest(@PathVariable Long userId){
        return this.friendService.getAllFriendsRequestByUserId(userId);
    }
    @PatchMapping("/cancel/{userId}/{friendId}")
    public void deleteFriend(@PathVariable Long friendId,@PathVariable Long userId){
        this.friendService.deleteFriend(userId,friendId);
    }
    @GetMapping("/isFriend/{userId}/{friendId}")
    public boolean getFriendBoolean(@PathVariable Long friendId,@PathVariable Long userId){
        return this.friendService.getFriendId(userId,friendId);
    }
}
