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
    public void sendShippedOrderMessage(Order order) {

        if (!AWSSESConfig.ENABLE) return;

        if (order == null) throw new SESException("Order cannot be null");

        try {

            //todo
            StringBuilder builder = new StringBuilder();

            for (String product : order.getProducts()) {
                String[] item = product.split(";");
                builder.append("商品").append(item[0]).append(item[1]).append("數量：").append(item[2]).append("\n");
            }

            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(order.getMail()))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(builder.toString())))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(AWSSESConfig.SUBJECT)))
                    .withSource(AWSSESConfig.FROM);
            client.sendEmail(request);
        } catch (Exception e) {
            throw new SESException(e.getMessage());
        }

    }
}
