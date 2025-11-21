package com.example.notifications.service;

import com.example.notifications.dto.MailDto;

public interface EmailSendService {
    String sendEmail(MailDto mailDto);
}
