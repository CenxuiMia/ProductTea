package com.cenxui.shop.aws.dynamodb.exceptions.server.order;

import com.cenxui.shop.aws.dynamodb.exceptions.client.RepositoryClientException;
import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;
import com.cenxui.shop.repositories.order.Order;

public class OrderPaymentMethodCannotNullException extends RepositoryServerException {
    public OrderPaymentMethodCannotNullException(Order order) {
        super("Order paymentMethod field cannot be null,  order :" + order);
    }
}
