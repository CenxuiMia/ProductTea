package com.cenxui.shop.aws.dynamodb.exceptions.client.coupon;

import com.cenxui.shop.aws.dynamodb.exceptions.client.RepositoryClientException;

public class CouponCannotUsedException extends RepositoryClientException {
    public CouponCannotUsedException(String couponMail, String couponType) {
        super(String.format("Coupon mail :%s coponType :%s cannot use.", couponMail, couponType));
    }
}
