package com.cenxui.shop.aws.dynamodb.exceptions.server.order;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class OrderCouponNotAllowException extends RepositoryServerException {
    public OrderCouponNotAllowException(String couponMail, String couponType) {
        super(String.format("couponMail :%s couponType :%s not allow", couponMail, couponType));
    }
}
