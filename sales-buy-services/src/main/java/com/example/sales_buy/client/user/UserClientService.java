package com.example.sales_buy.client.user;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserClientService {
    private final WebClient webClient;
    public UserClientService(WebClient webClient){
        this.webClient=webClient;
    }

    public UserClient getUser(Long id){
        return webClient.get()
                .uri("http://user-service:8090/api/user/"+id)
                .retrieve()
                .bodyToMono(UserClient.class)
                .block();
    }
    public void sendEmailFriends(Long id){
        webClient.post()
                .uri("http://user-service:8090/api/friend/listing/"+id)
                .retrieve()
                .toBodilessEntity()
                .block();

    }
}
