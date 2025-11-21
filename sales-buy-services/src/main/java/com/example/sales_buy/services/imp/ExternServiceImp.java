package com.example.sales_buy.services.imp;

import com.example.sales_buy.client.card.CardClient;
import com.example.sales_buy.client.card.CardClientService;
import com.example.sales_buy.client.mail.MailClient;
import com.example.sales_buy.client.mail.MailClientService;
import com.example.sales_buy.client.user.UserClient;
import com.example.sales_buy.client.user.UserClientService;
import com.example.sales_buy.services.ExternService;
import org.springframework.stereotype.Service;

@Service
public class ExternServiceImp implements ExternService {
    private final MailClientService mailClientService;
    private final UserClientService userClientService;
    private final CardClientService cardClientService;

    public ExternServiceImp(MailClientService mailClientService,UserClientService userClientService,
                            CardClientService cardClientService){
        this.cardClientService=cardClientService;
        this.mailClientService=mailClientService;
        this.userClientService=userClientService;
    }

    @Override
    public void sendMailExtern(Long buyerId, int quantity, Long sellerId, Long cardId) {
        UserClient buyer=this.userClientService.getUser(buyerId);
        UserClient seller=this.userClientService.getUser(sellerId);
        CardClient card=this.cardClientService.getCard(cardId);
        MailClient message=new MailClient();
        message.setUserId(buyerId);
        message.setToEmail(seller.getEmail());
        message.setSubject("Aviso de Venta");
        message.setBody("Buenas "+seller.getUsername()+" ,se le avisa que el usuario "
        +buyer.getUsername()+" le acaba de comprar "+quantity+" cartas de "+card.getName()
        +". Entra a la pagina para mas detalles.");
        this.mailClientService.sendEmail(message);
    }

    @Override
    public void sendListingEmail(Long userId) {
        this.userClientService.sendEmailFriends(userId);
    }
}
