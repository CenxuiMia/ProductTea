package com.cenxui.shop.aws.dynamodb.repositories;

import com.cenxui.shop.repositories.coupon.CouponRepository;
import org.junit.Test;

import static org.junit.Assert.*;

public class DynamoDBCouponBaseRepositoryTest {
    private CouponRepository couponRepository = DynamoDBRepositoryService
            .getCouponRepository(Config.REGION, Config.COUPON_TABLE);

    @Test
    public void addSignUpCoupon() throws Exception {
        couponRepository.addSignUpCoupon("cenxuilin@gmail.com");

    }

    @Test
    public void addInvitationCoupon() throws Exception {
    }

    @Test
    public void useCoupon() throws Exception {
    }

    @Test
    public void getCouponsByOwnerMail() throws Exception {
    }

}