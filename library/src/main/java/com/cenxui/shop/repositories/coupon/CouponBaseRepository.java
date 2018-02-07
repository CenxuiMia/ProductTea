package com.cenxui.shop.repositories.coupon;

import com.cenxui.shop.repositories.Repository;

public interface CouponBaseRepository extends Repository {

    Coupon addCoupon(Coupon coupon);

    Coupon addSignUpCoupon(String mail);

    Coupon addInvitationCoupon(String mail, String invitationMail);

    Coupon useCoupon(String couponMail, String couponType, String mail);

    Coupons getCouponsByOwnerMail(String ownerMail);

    Coupons getCouponsByOwnerMail(String ownerMail, CouponOwnerLastKey lastKey);
}
