package com.cenxui.shop.repositories.coupon.type;

import com.cenxui.shop.repositories.coupon.Coupon;
import com.cenxui.shop.repositories.coupon.CouponStatus;
import com.cenxui.shop.util.TimeUtil;

public class CouponType {
    public static final String INVITATION = "invitation";
    public static final String SIGN_UP = "signUp";

    private static final int couponExprationDays = 30;

    public static Coupon getSignUpCoupon(String mail) {
        Long expirationTime = TimeUtil.getCouponExpirationTime(couponExprationDays);

        return Coupon.of(
                mail,
                CouponType.SIGN_UP,
                mail,
                CouponStatus.ACTIVE,
                expirationTime,
                null
        );
    }

    public static Coupon getInvitationCoupon(String mail, String invitationMail) {
        Long expirationTime = TimeUtil.getCouponExpirationTime(couponExprationDays);

        return Coupon.of(
                mail,
                CouponType.INVITATION,
                invitationMail,
                CouponStatus.ACTIVE,
                expirationTime,
                null
        );
    }

    //add all coupon type here
}
