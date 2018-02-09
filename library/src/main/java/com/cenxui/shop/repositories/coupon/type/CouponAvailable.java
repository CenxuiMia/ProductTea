package com.cenxui.shop.repositories.coupon.type;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class CouponAvailable {
    List<String> coupons;
}
