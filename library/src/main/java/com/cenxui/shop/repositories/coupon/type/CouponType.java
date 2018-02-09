package com.cenxui.shop.repositories.coupon.type;

import com.cenxui.shop.repositories.coupon.Coupon;
import com.cenxui.shop.repositories.coupon.CouponStatus;
import com.cenxui.shop.repositories.coupon.type.exception.CouponActivitiesException;
import com.cenxui.shop.util.TimeUtil;

import java.util.*;

/**
 * add new coupon type in this class
 *
 */
public class CouponType {
    public static final String INVITATION = "invitation";
    public static final String SIGN_UP = "signUp";
    //todo add coupon

    private static final Map<String, CouponActivity> activityMap;
    private static final Set<String> couponTypes;

    static {
        Map<String, CouponActivity> map = new HashMap<>();
        map.put(INVITATION, new Discount50CouponActivity());
        map.put(SIGN_UP, new Discount50CouponActivity());
        //todo add activity

        activityMap = Collections.unmodifiableMap(map);
        couponTypes = activityMap.keySet();
    }

    private static final int couponExpirationDays = 30;

    public static boolean contain(String couponType) {
        return couponTypes.contains(couponType);
    }

    public static Set<String> getCouponTypeSet() {
        return couponTypes;
    }

    public static Coupon getSignUpCoupon(String mail) {
        Long expirationTime = TimeUtil.getCouponExpirationTime(couponExpirationDays);

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
        Long expirationTime = TimeUtil.getCouponExpirationTime(couponExpirationDays);

        return Coupon.of(
                mail,
                CouponType.INVITATION,
                invitationMail,
                CouponStatus.ACTIVE,
                expirationTime,
                null
        );
    }
    //todo coupon type here

    public static CouponActivity getCouponActivity(String couponType) throws CouponActivitiesException {
        if (!activityMap.containsKey(couponType)) {
            throw new CouponActivitiesException();
        }

        return activityMap.get(couponType);

    }
}
