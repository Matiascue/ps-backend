package com.example.sales_buy.client.mail;

import lombok.Data;

@Data
public class MailClient {
    private String toEmail;
    private String subject;
    private String body;
    private Long userId;
}
