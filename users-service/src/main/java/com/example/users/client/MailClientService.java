package com.example.users.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MailClientService {
    private final WebClient webClient;
    public MailClientService(WebClient webClient){
        this.webClient=webClient;
    }

    public String sendEmail(MailClient mail){
        return webClient.post()
                .uri("http://notification-services:8083/api/gmail/send")
                .bodyValue(mail)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
