package com.cenxui.shop.aws.dynamodb.exceptions.client.coupon;

import com.cenxui.shop.aws.dynamodb.exceptions.client.RepositoryClientException;

public class CouponCannotAddException extends RepositoryClientException {
    public CouponCannotAddException(String mail, String couponType) {
        super(String.format("Coupon %s %s already exited ", mail, couponType));
    }
}
