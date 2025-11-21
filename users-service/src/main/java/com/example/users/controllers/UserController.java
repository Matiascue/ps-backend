package com.example.users.controllers;

import com.example.users.dto.UserDtoInfo;
import com.example.users.dto.post.UserPostDto;
import com.example.users.dto.update.UserUpdateDto;
import com.example.users.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/{id}")
    public UserDtoInfo getById(@PathVariable Long id){
        return  userService.getById(id);
    }
    @GetMapping("/all")
    public List<UserDtoInfo> getAll(){
        return  userService.getAll();
    }
    @GetMapping("/user-actives")
    public Map<String,Long>getUseraActivityStats(){
        return userService.getUserActiveStats();
    }
    @PostMapping("/register")
    public UserDtoInfo registerUser(@RequestBody UserPostDto userPostDto){
        return userService.registerUser(userPostDto);
    }
    @PutMapping("/username")
    public UserDtoInfo usernameUpdate(@RequestBody UserUpdateDto userUpdateDto){
        return userService.userNameUpdate(userUpdateDto);
    }
    @DeleteMapping("/unsubscribe/{id}")
    public UserDtoInfo unsubscribeUser(@PathVariable Long id){
        return  userService.unsubscribeUser(id);
    }
    @PostMapping("/users/{id}/upload-profile")
    public ResponseEntity<Map<String, String>> uploadProfilePicture(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        String response=userService.uploadProfilePicture(id, file);
        logger.info("Respuesta de la imagen: {}", response);
        return ResponseEntity.ok(Map.of("message",response ));
    }
}
