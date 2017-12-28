package com.cenxui.shop.web.app.sns;

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
    private AmazonSNS snsClient = AmazonSNSClientBuilder.standard()
            .withRegion(AWSSNSConfig.REGION).build();

    @Override
    public void sendMessage(Order order) {
        //todo throw exception

        String message = "親愛的顧客感謝你在皇飲下單ＮＴ＄" + order.getPrice();
        String phoneNumber = "+886" + order.getPhone().substring(1);
        Map<String, MessageAttributeValue> smsAttributes =
                new HashMap<String, MessageAttributeValue>();
        //<set SMS attributes>
        sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);
    }

    private void sendSMSMessage(AmazonSNS snsClient, String message,
                                String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));
        System.out.println(result); // Prints the message ID.
    }
}
