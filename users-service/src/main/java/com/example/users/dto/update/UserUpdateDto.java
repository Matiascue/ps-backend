package com.example.users.dto.update;

import lombok.Data;

@Data
public class UserUpdateDto {
    private Long id;
    private String username;
    private String email;
    private String password;
}
