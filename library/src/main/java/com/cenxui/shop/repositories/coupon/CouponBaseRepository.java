package com.cenxui.shop.repositories.coupon;

import com.cenxui.shop.repositories.Repository;

public interface CouponBaseRepository extends Repository {

    Coupon addCoupon(Coupon coupon);

    Coupons getCouponsByOwnerMail(String ownerMail);

    Coupons getCouponsByOwnerMail(String ownerMail, CouponOwnerLastKey lastKey);
}
