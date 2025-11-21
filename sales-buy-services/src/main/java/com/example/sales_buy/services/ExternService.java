package com.example.sales_buy.services;

public interface ExternService {
    void sendMailExtern(Long buyerId,int quantity,Long sellerId,Long cardId);
    void sendListingEmail(Long userId);
}
