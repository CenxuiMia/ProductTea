package com.cenxui.shop.aws.dynamodb.exceptions.server.order;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;
import com.cenxui.shop.repositories.order.Order;

public class OrderShippedWayCannotNullException extends RepositoryServerException {
    public OrderShippedWayCannotNullException(Order order) {
        super("Order products field cannot be null,  order :" + order);
    }
}