package com.cenxui.tea.app.aws.dynamodb.exceptions.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.util.JsonUtil;

public class OrderCannotAddException extends RepositoryException {
    public OrderCannotAddException(Order order) {
        super("Order already exists. Cannot add : " + JsonUtil.mapToJson(order));
    }
}
