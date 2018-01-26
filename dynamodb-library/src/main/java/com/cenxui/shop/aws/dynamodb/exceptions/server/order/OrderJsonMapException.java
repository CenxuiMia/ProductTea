package com.cenxui.shop.aws.dynamodb.exceptions.server.order;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class OrderJsonMapException extends RepositoryServerException {
    public OrderJsonMapException(String orderJson) {
        super("Order table item maps to order object fail, Order : " + orderJson);
    }
}
