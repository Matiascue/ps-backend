package com.example.users.client;

import lombok.Data;

@Data
public class MailClient {
    private String toEmail;
    private String subject;
    private String body;
    private Long userId;
}
