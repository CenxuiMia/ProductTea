package com.cenxui.shop.aws.dynamodb.exceptions.server.coupon;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class CouponPrimaryKeyCannotNullException extends RepositoryServerException {

    public CouponPrimaryKeyCannotNullException() {
        super("coupon primary key cannot null");
    }
}
