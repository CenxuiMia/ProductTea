package com.cenxui.shop.repositories.coupon;

import lombok.Value;

@Value(staticConstructor = "of")
public class CouponOwnerLastKey extends Key {
    String mail;
    String couponType;
    String ownerMail;
    String couponStatus;
}
