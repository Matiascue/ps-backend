package com.example.users.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserDtoInfo {
    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean active;
    private String rol;
    private LocalDateTime expirationDate;
    private LocalDateTime lastPasswordChangeDate;
    private LocalDate registerDate;
    private String token;
    private String profilePicture;
}
