package com.cenxui.shop.aws.sns;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.cenxui.shop.web.app.config.AWSSNSConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SNSTest {

    @Test
    public void setUp() {
//        AmazonSNSClient snsClient = new AmazonSNSClient();
//        String message = "親愛的顧客感謝你在皇飲下單";
//        String phoneNumber = "+886972858256";
//        Map<String, MessageAttributeValue> smsAttributes =
//                new HashMap<String, MessageAttributeValue>();
//        //<set SMS attributes>
//        sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);
    }

    @Test
    public void sendMessage() {
        System.out.println(String.format(AWSSNSConfig.MESSAGE, 500));

    }

    private void sendSMSMessage(AmazonSNSClient snsClient, String message,
                                      String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));
        System.out.println(result); // Prints the message ID.
    }

}
