package com.cenxui.shop.util;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

public class TimeUtilTest {
    @Test
    public void getNowDate() throws Exception {
        System.out.println(TimeUtil.getNowDate());
    }

    @Test
    public void getNowTime() throws Exception {
        System.out.println(TimeUtil.getNowTime());

    }

    @Test
    public void getNowDateTime() throws Exception {
        System.out.println(TimeUtil.getNowDateTime());
    }

    @Test
    public void getCouponExpiredTime() {
        System.out.println(TimeUtil.getCouponExpireDayTime(30));
    }

    @Test
    public void getCouponExpirationTime() {

        System.out.println(new Date(TimeUtil.getCouponExpirationTime(1)));
    }

    @Test
    public void stringToDateTime() {
        long curr = System.currentTimeMillis();
        String time = "2018-03-09T14:02:31.768";
        LocalDateTime.parse(time).minusDays(30);
//        System.out.println();
        System.out.println(System.currentTimeMillis() - curr);

    }

}