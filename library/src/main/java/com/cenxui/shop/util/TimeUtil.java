package com.cenxui.shop.util;

import java.time.*;

public class TimeUtil {
    public static String getNowDate(){
        return getLocalDateTimeNow().toLocalDate().toString();
    }

    public static String getNowTime() {
        return getLocalDateTimeNow().toLocalTime().toString().substring(0,8);
    }

    public static String getNowDateTime() {
        return  getLocalDateTimeNow().toString().substring(0,19);
    }

    public static LocalDateTime getCouponExpireDayTime(int days) {
        return getLocalDateTimeNow().plusDays(days);
    }

    public static long getCouponExpirationTime(int days) {
        return System.currentTimeMillis() + days * 86_400_000L;
    }

    private static LocalDateTime getLocalDateTimeNow() {
        return LocalDateTime.now(Clock.system(ZoneId.of("UTC+8")));
    }
}
