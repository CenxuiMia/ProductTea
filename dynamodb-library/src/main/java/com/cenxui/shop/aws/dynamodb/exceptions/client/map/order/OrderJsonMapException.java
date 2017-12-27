package com.cenxui.shop.aws.dynamodb.exceptions.client.map.order;

import com.cenxui.shop.aws.dynamodb.exceptions.client.map.JsonMapException;

public class OrderJsonMapException extends JsonMapException {
    public OrderJsonMapException(String orderJson) {
        super("Order json value maps to order object fail, Order : " + orderJson);
    }
}