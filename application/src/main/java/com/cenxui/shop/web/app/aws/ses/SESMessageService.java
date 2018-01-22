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
    public void sendMessage(Order order) {
        //todo exception
        try {
            String message = String.format(AWSSESConfig.MESSAGE, order.getPrice());

            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(order.getMail()))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(AWSSESConfig.HTMLBODY))
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(message)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(AWSSESConfig.SUBJECT)))
                    .withSource(AWSSESConfig.FROM);
            client.sendEmail(request);
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent. Error message: "
                    + ex.getMessage());
        }
    }
}
