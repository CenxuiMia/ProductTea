package com.cenxui.tea.app.aws.dynamodb.exceptions.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.map.MapJsonException;
import com.cenxui.tea.app.repositories.order.Order;

public class OrderMapJsonException extends MapJsonException {
    public OrderMapJsonException(Object order) {
        super("Order map to Json value fail," + order);
    }
}
