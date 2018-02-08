package com.cenxui.shop.aws.dynamodb.exceptions.server.order;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class OrderCouponActivityNotExistException extends RepositoryServerException {
    public OrderCouponActivityNotExistException(String couponType) {
        super(String.format("No couponAcivity related to couponType :%s", couponType));
    }
}
