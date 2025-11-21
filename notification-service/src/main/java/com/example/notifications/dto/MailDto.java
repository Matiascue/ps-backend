package com.example.notifications.dto;

import lombok.Data;

@Data
public class MailDto {
    private String toEmail;
    private String subject;
    private String body;
    private Long userId;
}
