package com.cenxui.shop.aws.dynamodb.exceptions.server.order;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class OrderPurchaserNotAllowedExcetpion extends RepositoryServerException {
    public OrderPurchaserNotAllowedExcetpion(String purchaser) {
        super("Order purchaser field is not allowed.");
    }
}
