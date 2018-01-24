package com.cenxui.shop.admin.app.aws.ses;

import com.cenxui.shop.admin.app.config.AWSSESConfig;
import com.cenxui.shop.admin.app.service.MessageService;
import com.cenxui.shop.repositories.order.Order;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;

public class SESMessageService implements MessageService {
    private final  AmazonSimpleEmailService client =
            AmazonSimpleEmailServiceClientBuilder.standard()
                    .withRegion(AWSSESConfig.REGION).build();

    @Override
    public void sendPayOrderMessage(Order paidOrder) {
        if (!AWSSESConfig.ENABLE) return;

        checkOrder(paidOrder);
        String message = String.format(
                AWSSESConfig.HTMLPAY,
                paidOrder.getMail(),
                paidOrder.getPaidDate(),
                paidOrder.getPrice());

        sendMail(paidOrder.getMail(), message, AWSSESConfig.PAY_SUBJECT);
    }

    @Override
    public void sendDePaidOrderMessage(Order paidOrder) {
        if (!AWSSESConfig.ENABLE) return;

        checkOrder(paidOrder);

        //todo
    }



    @Override
    public void sendShipOrderMessage(Order shippedOrder) {
        if (!AWSSESConfig.ENABLE) return;

        checkOrder(shippedOrder);
        String message = String.format(
                AWSSESConfig.HTMLSHIP,
                shippedOrder.getPaidDate());


        sendMail(shippedOrder.getMail(), message, AWSSESConfig.SHIP_SUBJECT);

    }

    private void sendMail(String mail, String message, String subject) {

        try {
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(mail))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(message)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(subject)))
                    .withSource(AWSSESConfig.FROM);
            client.sendEmail(request);
        } catch (Exception e) {
            throw new SESException(e.getMessage());
        }
    }

    @Override
    public void sendDeShippedOrderMessage(Order shippedOrder) {
        if (!AWSSESConfig.ENABLE) return;
        checkOrder(shippedOrder);
        //todo
    }

    private void checkOrder(Order order) {
        if (order == null) throw new SESException("Order cannot be null");
    }

}
