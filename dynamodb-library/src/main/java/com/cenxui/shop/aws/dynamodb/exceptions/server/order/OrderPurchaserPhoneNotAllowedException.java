package com.cenxui.shop.aws.dynamodb.exceptions.server.order;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class OrderPurchaserPhoneNotAllowedException extends RepositoryServerException {
    public OrderPurchaserPhoneNotAllowedException(String purchaserPhone) {
        super("Order purchaserPhone field is not allowed");
    }
}
