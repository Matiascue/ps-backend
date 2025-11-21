package com.example.sales_buy.client.user;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserClient {
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
}
