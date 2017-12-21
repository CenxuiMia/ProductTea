package com.cenxui.shop.aws.dynamodb.exceptions.client.order;

import com.cenxui.shop.aws.dynamodb.exceptions.client.RepositoryClientException;
import com.cenxui.shop.repositories.order.Order;
import com.cenxui.shop.util.JsonUtil;

public class OrderCannotAddException extends RepositoryClientException {
    public OrderCannotAddException(Order order) {
        super("Order already exists. Cannot add : " + JsonUtil.mapToJson(order));
    }
}
