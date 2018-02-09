package com.cenxui.shop.aws.dynamodb.repositories;

import com.cenxui.shop.repositories.coupon.Coupon;
import com.cenxui.shop.repositories.coupon.CouponRepository;
import com.cenxui.shop.repositories.coupon.CouponStatus;
import com.cenxui.shop.repositories.coupon.type.CouponType;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.*;

public class DynamoDBCouponBaseRepositoryTest {
    private CouponRepository couponRepository = DynamoDBRepositoryService
            .getCouponRepository(Config.REGION, Config.COUPON_TABLE);

    @Test
    public void addCoupon() {
        couponRepository.addCoupon(Coupon.of(
                "cenxuilin@gmail.com",
                CouponType.SIGN_UP,
                "cenxuilin@gmail.com",
                CouponStatus.ACTIVE,
                System.currentTimeMillis() + 100_000L,
                null
        ));

    }

    @Test
    public void testDate() {
        System.out.println(new Date(1518024119045L));
    }

    @Test
    public void addSignUpCoupon() throws Exception {
        System.out.println(couponRepository.addSignUpCoupon("cenxuilin@gmail.com"));

    }

    @Test
    public void addInvitationCoupon() throws Exception {
        System.out.println(
                couponRepository.addInvitationCoupon("cenxuilin@gmail.com", "mia@gmail.com"));
    }

    @Test
    public void useCoupon() throws Exception {
        couponRepository.useCoupon("cenxuilin@gmail.com", CouponType.SIGN_UP, "cenxuilin@gmail.com", LocalDateTime.now().toString());
//        couponRepository.useCoupon("cenxuilin@gmail.com", CouponType.INVITATION, "mia@gmail.com");
    }

    @Test
    public void getCouponsByOwnerMail() throws Exception {
    }

    @Test
    public void getAvailableCoupons() {
        System.out.println(couponRepository.getAvailableCouponsByOwnerMail("cenxuilin@gmail.com"));
    }
}