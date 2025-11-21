package com.example.users.services.imp;

import com.example.users.client.MailClient;
import com.example.users.client.MailClientService;
import com.example.users.services.ExternService;
import org.springframework.stereotype.Service;

@Service
public class ExternServiceImp implements ExternService {
    private final MailClientService mailClientService;

    public ExternServiceImp(MailClientService mailClientService){
        this.mailClientService=mailClientService;
    }

    @Override
    public void sendWelcomeMail(String email, Long userId, String username) {
        MailClient mailClient=new MailClient();
        mailClient.setToEmail(email);
        mailClient.setSubject("Bienvenido a TCG Pocket Shop!!");
        mailClient.setUserId(userId);
        mailClient.setBody("Bienvenido "+username+" a TCG Pocket Shop." +
                "Estamos muy contento de que nos haya elegido para vender y comprar tus cartas de pokemon.");
        this.mailClientService.sendEmail(mailClient);
    }

    @Override
    public void sendFriendRequestMail(String email, Long userId, String username,String friendUsername) {
        MailClient mailClient=new MailClient();
        mailClient.setToEmail(email);
        mailClient.setSubject("Solicitud de Amistad");
        mailClient.setUserId(userId);
        mailClient.setBody("Buenas "+friendUsername+"." +
                "Se le comunica que el usuario "+username+" quiere ser su amigo.");
        this.mailClientService.sendEmail(mailClient);
    }

    @Override
    public void sendListingFriendsMail(String email, Long userId, String username, String friendUsername) {
        MailClient mailClient=new MailClient();
        mailClient.setToEmail(email);
        mailClient.setSubject("Un amigo puso cartas en venta");
        mailClient.setUserId(userId);
        mailClient.setBody("Buenas "+username+"." +
                "Se le comunica que el usuario "+friendUsername+" puso en ventas unas cartas.");
        this.mailClientService.sendEmail(mailClient);
    }
}
