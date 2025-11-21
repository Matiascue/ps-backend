package com.example.notifications.controller;

import com.example.notifications.dto.MailDto;
import com.example.notifications.service.EmailSendService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gmail")
@CrossOrigin("*")
public class NotificationController {

    private final EmailSendService emailSendService;

    public NotificationController(EmailSendService emailSendService){
        this.emailSendService=emailSendService;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestBody MailDto mailDto){
        return this.emailSendService.sendEmail(mailDto);
    }
}
