package com.cenxui.shop.aws.dynamodb.exceptions.server.order;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class OrderReceiverNotAllowedException extends RepositoryServerException {
    public OrderReceiverNotAllowedException(String receiver) {
        super("Order receiver field is not allowed " + receiver);
    }
}
