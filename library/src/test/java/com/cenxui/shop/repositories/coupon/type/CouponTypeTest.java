package com.cenxui.shop.repositories.coupon.type;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class CouponTypeTest {
    @Test
    public void contain() throws Exception {
        System.out.println(CouponType.contain(null));

    }

    @Test
    public void getSignUpCoupon() throws Exception {
    }

    @Test
    public void getInvitationCoupon() throws Exception {
    }

    @Test
    public void getAvailableCoupon() {
        final Set<String> set = new HashSet<>(Arrays.asList(CouponType.SIGN_UP));

        Set<String> result = CouponType.getCouponTypeSet().stream().filter(
                (s)->{
                    return !set.contains(s);
                }
        ).collect(Collectors.toSet());
        System.out.println(result);
    }

}