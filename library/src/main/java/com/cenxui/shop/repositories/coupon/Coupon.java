package com.cenxui.shop.repositories.coupon;

import lombok.Value;

@Value
public class Coupon {
    public static final String MAIL = "mail";
    public static final String COUPON_TYPE = "couponType";
    public static final String OWNER_MAIL = "ownerMail";
    public static final String COUPON_STATUS = "couponStatus";
    public static final String EXPIRED_DATE_TIME = "expiredDateTime";

    String mail;
    String couponType;
    String ownerMail;
    String couponStatus;
    String expiredDateTime;
}
