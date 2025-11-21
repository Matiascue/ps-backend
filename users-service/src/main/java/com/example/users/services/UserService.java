package com.example.users.services;

import com.example.users.dto.UserDtoInfo;
import com.example.users.dto.login.UserLoginDto;
import com.example.users.dto.post.UserPostDto;
import com.example.users.dto.update.UserUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {
    UserDtoInfo registerUser(UserPostDto userPostDto);
    UserDtoInfo userNameUpdate(UserUpdateDto userUpdateDto);
    UserDtoInfo unsubscribeUser(Long id);
    UserDtoInfo login(UserLoginDto userLoginDto);
    UserDtoInfo getById(Long id);
    List<UserDtoInfo> getAll();
    Map<String,Long>getUserActiveStats();
   String uploadProfilePicture(Long userId, MultipartFile file) throws IOException;
}
