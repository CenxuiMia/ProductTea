package com.cenxui.shop.aws.dynamodb.exceptions.server.order;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class OrderShippingAddressNotAllowedException extends RepositoryServerException {
    public OrderShippingAddressNotAllowedException(String shippingAddress) {
        super("Order shippingAddress field is not allowed " + shippingAddress);
    }
}
