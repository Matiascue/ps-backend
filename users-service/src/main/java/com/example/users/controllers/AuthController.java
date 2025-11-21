package com.example.users.controllers;

import com.example.users.dto.UserDtoInfo;
import com.example.users.dto.login.UserLoginDto;
import com.example.users.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService ){
        this.userService = userService;
    }

    @PostMapping("/login")
    public UserDtoInfo login(@RequestBody UserLoginDto userLoginDto){
        return userService.login(userLoginDto);
    }
}
