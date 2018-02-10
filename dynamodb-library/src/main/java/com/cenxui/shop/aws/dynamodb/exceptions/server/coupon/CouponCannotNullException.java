package com.cenxui.shop.aws.dynamodb.exceptions.server.coupon;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class CouponCannotNullException extends RepositoryServerException {
    public CouponCannotNullException() {
        super("coupon cannot null");
    }
}
