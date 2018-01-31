package com.cenxui.shop.aws.dynamodb.exceptions.server.order;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class OrderShippingWayException extends RepositoryServerException {
    public OrderShippingWayException(String shippingWay) {
        super("Order shippingWay field is not allowed " + shippingWay);
    }
}
