package com.cenxui.shop.aws.dynamodb.exceptions.server.order;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class OrderShippedWayNotAllowedException extends RepositoryServerException {
    public OrderShippedWayNotAllowedException(String e) {
        super("order shippedWay not allow shippedWay :" + e);
    }
}
