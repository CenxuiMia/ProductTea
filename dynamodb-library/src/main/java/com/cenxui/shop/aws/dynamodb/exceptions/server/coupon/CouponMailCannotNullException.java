package com.cenxui.shop.aws.dynamodb.exceptions.server.coupon;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class CouponMailCannotNullException extends RepositoryServerException{
    public CouponMailCannotNullException() {
        super("coupon mail cannot be null");
    }
}
