package com.example.users.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "rol_id",nullable = false)
    private RolEntity role;
    private LocalDateTime expirationDate;
    private LocalDateTime lastPasswordChangeDate;
    private LocalDate registerDate;
    private String profilePicture;
}
