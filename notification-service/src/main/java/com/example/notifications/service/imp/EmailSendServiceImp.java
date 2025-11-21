package com.example.notifications.service.imp;

import com.example.notifications.dto.MailDto;
import com.example.notifications.entity.NotificationEntity;
import com.example.notifications.repository.NotificationRepository;
import com.example.notifications.service.EmailSendService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmailSendServiceImp implements EmailSendService {

    private final JavaMailSender mailSender;
    private final NotificationRepository notificationRepository;
    @Value("${email.username}")
    private String username;
    public EmailSendServiceImp(JavaMailSender javaMailSender,NotificationRepository notificationRepository){
        this.mailSender=javaMailSender;
        this.notificationRepository=notificationRepository;
    }

    @Override
    public String sendEmail(MailDto mailDto) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(mailDto.getToEmail());
        message.setText(mailDto.getBody());
        message.setSubject(mailDto.getSubject());
        this.saveMessage(mailDto);
        mailSender.send(message);
        return "Mail enviado con exito";
    }

    private void saveMessage(MailDto mailDto) {
        NotificationEntity notificationEntity=new NotificationEntity();
        notificationEntity.setBody(mailDto.getBody());
        notificationEntity.setEmailSender(mailDto.getToEmail());
        notificationEntity.setSendDate(LocalDate.now());
        notificationEntity.setTittle(mailDto.getSubject());
        notificationEntity.setStatus("Send");
        notificationEntity.setUserId(mailDto.getUserId());
        this.notificationRepository.save(notificationEntity);
    }
}
