package com.cenxui.shop.repositories.coupon;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class Coupons {
    List<Coupon> coupons;
    Key lastKey;
}
