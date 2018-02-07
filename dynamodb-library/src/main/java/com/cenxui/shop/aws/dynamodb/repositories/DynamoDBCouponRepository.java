package com.cenxui.shop.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.cenxui.shop.repositories.coupon.Coupon;
import com.cenxui.shop.repositories.coupon.CouponOwnerLastKey;
import com.cenxui.shop.repositories.coupon.CouponRepository;
import com.cenxui.shop.repositories.coupon.Coupons;

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
    public Coupon useCoupon(String couponMail, String couponType, String mail) {
        return couponBaseRepository.useCoupon(couponMail, couponType, mail);
    }

    @Override
    public Coupons getCouponsByOwnerMail(String ownerMail) {
        return couponBaseRepository.getCouponsByOwnerMail(ownerMail);
    }

    @Override
    public Coupons getCouponsByOwnerMail(String ownerMail, CouponOwnerLastKey lastKey) {
        return couponBaseRepository.getCouponsByOwnerMail(ownerMail, lastKey);
    }
}
