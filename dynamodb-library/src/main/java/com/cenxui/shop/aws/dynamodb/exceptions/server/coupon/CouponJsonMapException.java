package com.cenxui.shop.aws.dynamodb.exceptions.server.coupon;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class CouponJsonMapException extends RepositoryServerException {
    public CouponJsonMapException(String couponJson) {
        super("Coupon table item maps to coupon object fail, Coupon :" + couponJson);
    }
}
