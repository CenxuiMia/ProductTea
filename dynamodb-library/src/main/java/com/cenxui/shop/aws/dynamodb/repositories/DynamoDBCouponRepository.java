package com.cenxui.shop.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.cenxui.shop.repositories.coupon.Coupon;
import com.cenxui.shop.repositories.coupon.CouponOwnerLastKey;
import com.cenxui.shop.repositories.coupon.CouponRepository;
import com.cenxui.shop.repositories.coupon.Coupons;
import com.cenxui.shop.repositories.coupon.type.CouponAvailable;

class DynamoDBCouponRepository implements CouponRepository {

    private final DynamoDBCouponBaseRepository couponBaseRepository;

    DynamoDBCouponRepository(Table table) {
        this.couponBaseRepository = new DynamoDBCouponBaseRepository(table);
    }

    @Override
    public Coupon addCoupon(Coupon coupon) {
        return couponBaseRepository.addCoupon(coupon);
    }

    @Override
    public Coupon addSignUpCoupon(String mail) {
        return couponBaseRepository.addSignUpCoupon(mail);
    }

    @Override
    public Coupon addInvitationCoupon(String mail, String invitationMail) {
        return couponBaseRepository.addInvitationCoupon(mail, invitationMail);
    }

    @Override
    public Coupon useCoupon(String couponMail, String couponType, String mail, Long orderDateTime) {
        return couponBaseRepository.useCoupon(couponMail, couponType, mail, orderDateTime);
    }

    @Override
    public Coupons getCouponsByOwnerMail(String ownerMail) {
        return couponBaseRepository.getCouponsByOwnerMail(ownerMail);
    }

    @Override
    public Coupons getCouponByOwnerMail(String ownerMail, CouponOwnerLastKey couponOwnerLastKey) {
        return couponBaseRepository.getCouponByOwnerMail(ownerMail, couponOwnerLastKey);
    }

    @Override
    public CouponAvailable getAvailableCouponsByOwnerMail(String ownerMail) {
        return couponBaseRepository.getAvailableCouponsByOwnerMail(ownerMail);
    }

    @Override
    public CouponAvailable getAvailableCouponsByOwnerMail(String ownerMail, CouponOwnerLastKey couponOwnerLastKey) {
        return couponBaseRepository.getAvailableCouponsByOwnerMail(ownerMail, couponOwnerLastKey);
    }

    @Override
    public Coupons getActiveCouponsByOwnerMail(String ownerMail) {
        return couponBaseRepository.getActiveCouponsByOwnerMail(ownerMail);
    }

    @Override
    public Coupons getActiveCouponsByOwnerMail(String ownerMail, CouponOwnerLastKey lastKey) {
        return couponBaseRepository.getActiveCouponsByOwnerMail(ownerMail, lastKey);
    }
}
