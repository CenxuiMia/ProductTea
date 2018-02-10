package com.cenxui.shop.aws.dynamodb.exceptions.server.coupon;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class CouponTypeNotAllowedException extends RepositoryServerException {
    public CouponTypeNotAllowedException(String couponType) {
        super(String.format("couponType :%s not allow", couponType));
    }
}
