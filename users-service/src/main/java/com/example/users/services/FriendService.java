package com.example.users.services;

import com.example.users.dto.FriendInfoDto;
import com.example.users.dto.post.FriendPostDto;

import java.util.List;

public interface FriendService {
    FriendPostDto sendFriendRequest(FriendPostDto friendPostDto);
    void acceptFriendRequest(Long friendId);
    void cancelFriendRequest(Long friendId);
    void sendListingEmail(Long userId);
    void deleteFriend(Long userId,Long friendId);
    boolean getFriendId(Long userId,Long userFriendId);
    List<FriendInfoDto>getAllFriendsByUserId(Long userId);
    List<FriendInfoDto>getAllFriendsRequestByUserId(Long userId);
}
