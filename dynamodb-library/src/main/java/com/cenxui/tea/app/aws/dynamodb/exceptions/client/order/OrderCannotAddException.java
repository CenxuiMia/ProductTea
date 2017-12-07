package com.cenxui.tea.app.aws.dynamodb.exceptions.client.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.client.RepositoryClientException;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.util.JsonUtil;

public class OrderCannotAddException extends RepositoryClientException {
    public OrderCannotAddException(Order order) {
        super("Order already exists. Cannot add : " + JsonUtil.mapToJson(order));
    }
}
