package com.example.users.services;

public interface ExternService {
    void sendWelcomeMail(String email,Long userId,String username);
    void sendFriendRequestMail(String email,Long userId,String username,String friendUsername);
    void sendListingFriendsMail(String email,Long userId,String username,String friendUsername);
}
