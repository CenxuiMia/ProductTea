package com.cenxui.tea.app.aws.dynamodb.exceptions.map;

import com.cenxui.tea.app.repositories.order.Order;

public class OrderMapJsonException extends MapJsonException {
    public OrderMapJsonException(String e, Order order) {
        super("Order map to Json value fail," + order + ",error :" + e);
    }
}
