package com.cenxui.shop.repositories.coupon.attribute;

import com.cenxui.shop.repositories.coupon.type.CouponType;

public class CouponAttributeFilter {
    public static boolean checkCouponType(String couponType) {
        return CouponType.contain(couponType);
    }
}
