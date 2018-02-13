package com.cenxui.shop.repositories.coupon;

import lombok.Value;

@Value(staticConstructor = "of")
public class Coupon {
    public static final String MAIL = "mail";
    public static final String COUPON_TYPE = "couponType";
    public static final String OWNER_MAIL = "ownerMail";
    public static final String COUPON_STATUS = "couponStatus";
    public static final String COUPON_ACTIVITY = "couponActivity";
    public static final String EXPIRATION_TIME = "expirationTime";
    public static final String ORDER_DATETIME = "orderDateTime";

    String mail;
    String couponType;
    String ownerMail;
    String couponStatus;
    String couponActivity;
    Long expirationTime;
    Long orderDateTime;
}
