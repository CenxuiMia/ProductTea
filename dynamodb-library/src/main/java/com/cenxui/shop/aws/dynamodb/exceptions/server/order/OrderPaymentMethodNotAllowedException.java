package com.cenxui.shop.aws.dynamodb.exceptions.server.order;

import com.cenxui.shop.aws.dynamodb.exceptions.client.RepositoryClientException;
import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;
import com.cenxui.shop.repositories.order.Order;

public class OrderPaymentMethodNotAllowedException extends RepositoryServerException {
    public OrderPaymentMethodNotAllowedException(String paymentMethod) {
        super("Order paymentMethod field is not allowed :" + paymentMethod);
    }
}
