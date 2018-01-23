package com.cenxui.shop.web.app.aws.ses;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.cenxui.shop.repositories.order.Order;
import com.cenxui.shop.web.app.config.AWSSESConfig;
import com.cenxui.shop.web.app.service.SendMessageService;

public class SESMessageService implements SendMessageService {
    private final  AmazonSimpleEmailService client =
            AmazonSimpleEmailServiceClientBuilder.standard()
                    .withRegion(AWSSESConfig.REGION).build();

    @Override
    public void sendOrderMessage(Order order) {
        if (!AWSSESConfig.ENABLE) return;

        if (order == null) throw new SESException("Order cannot be null");

        try {

            //todo
            String message = String.format(AWSSESConfig.HTMLBODY, order.getPrice());

            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(order.getMail()))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(message)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(AWSSESConfig.SUBJECT)))
                    .withSource(AWSSESConfig.FROM);
            client.sendEmail(request);
        } catch (Exception e) {
           throw new SESException(e.getMessage());
        }
    }
}
