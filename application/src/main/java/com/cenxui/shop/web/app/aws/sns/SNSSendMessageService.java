package com.cenxui.shop.web.app.aws.sns;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.cenxui.shop.repositories.order.Order;
import com.cenxui.shop.web.app.config.AWSSNSConfig;
import com.cenxui.shop.web.app.service.SendMessageService;

import java.util.HashMap;
import java.util.Map;

public class SNSSendMessageService implements SendMessageService {
    private final AmazonSNS snsClient = AmazonSNSClientBuilder.standard()
            .withRegion(AWSSNSConfig.REGION).build();

    @Override
    public void sendOrderMessage(Order order) {
        if (!AWSSNSConfig.ENABLE) return;

        if (order == null) throw new SNSException("Order cannot be null");

        //todo
        try {
            String message = String.format(AWSSNSConfig.MESSAGE, order.getPrice());
            String phoneNumber = "+886" + order.getPurchaserPhone().substring(1);
            Map<String, MessageAttributeValue> smsAttributes =
                    new HashMap<String, MessageAttributeValue>();
            //<set SMS attributes>
            sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);
        }catch (Exception e) {
            throw new SNSException(e.getMessage());
        }
    }

    private void sendSMSMessage(AmazonSNS snsClient, String message,
                                String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));

    }
}
