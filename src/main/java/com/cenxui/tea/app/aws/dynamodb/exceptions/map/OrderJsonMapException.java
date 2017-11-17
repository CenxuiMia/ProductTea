package com.cenxui.tea.app.aws.dynamodb.exceptions.map;

import com.cenxui.tea.app.repositories.order.Order;

public class OrderJsonMapException extends JsonMapException {
    public OrderJsonMapException(String e, String orderJson) {
        super("Order json value maps to order object fail, Order : " + orderJson + ", error :" + e);
    }
}
