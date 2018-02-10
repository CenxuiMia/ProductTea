package com.cenxui.shop.repositories.coupon;

import com.cenxui.shop.repositories.Repository;
import com.cenxui.shop.repositories.coupon.type.CouponAvailable;

public interface CouponBaseRepository extends Repository {

    Coupon addCoupon(Coupon coupon);

    Coupon addSignUpCoupon(String mail);

    Coupon addInvitationCoupon(String mail, String invitationMail);

    Coupon useCoupon(String couponMail, String couponType, String mail, String orderDateTime);

    Coupons getCouponsByOwnerMail(String ownerMail);

    Coupons getCouponByOwnerMail(String ownerMail, CouponOwnerLastKey couponOwnerLastKey);

    Coupons getActiveCouponsByOwnerMail(String ownerMail);

    Coupons getActiveCouponsByOwnerMail(String ownerMail, CouponOwnerLastKey lastKey);

    CouponAvailable getAvailableCouponsByOwnerMail(String ownerMail);

    CouponAvailable getAvailableCouponsByOwnerMail(String ownerMail, CouponOwnerLastKey couponOwnerLastKey);
}
