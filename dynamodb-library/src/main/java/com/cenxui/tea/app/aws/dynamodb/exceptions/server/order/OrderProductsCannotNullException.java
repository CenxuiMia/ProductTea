package com.cenxui.tea.app.aws.dynamodb.exceptions.server.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.server.RepositoryServerException;
import com.cenxui.tea.app.repositories.order.Order;

public class OrderProductsCannotNullException extends RepositoryServerException {
    public OrderProductsCannotNullException(Order order) {
        super("Order products field cannot be null,  order :" + order);
    }
}
