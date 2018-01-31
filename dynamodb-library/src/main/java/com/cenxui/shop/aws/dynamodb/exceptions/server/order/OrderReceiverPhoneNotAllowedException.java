package com.cenxui.shop.aws.dynamodb.exceptions.server.order;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class OrderReceiverPhoneNotAllowedException extends RepositoryServerException {
    public OrderReceiverPhoneNotAllowedException(String receiverPhone) {
        super("Order receiverPhone field is not allowed" + receiverPhone);
    }
}
