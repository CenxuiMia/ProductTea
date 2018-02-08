package com.cenxui.shop.repositories.coupon.type.activity;

import com.cenxui.shop.repositories.coupon.type.CouponType;

/**
 * factory method connect the relationship between coupon and activity
 * add new activity in this class
 */
public class CouponActivities {

    public static CouponActivity getCouponActivity(String couponType) {
        switch (couponType) {
            case CouponType.SIGN_UP:
                return new Discount50CouponActivity();
            case CouponType.INVITATION:
                return new Discount50CouponActivity();
                //todo add new activity
        }
        return null;
    }
}
