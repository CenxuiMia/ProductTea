package com.cenxui.shop.aws.dynamodb.exceptions.server.order;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;
import com.cenxui.shop.repositories.order.Order;

import java.util.List;

public class OrderProductsNotAllowedException extends RepositoryServerException {
    public OrderProductsNotAllowedException(List<String> products) {
        super("Order products field is not allowed :" + products);
    }
}
