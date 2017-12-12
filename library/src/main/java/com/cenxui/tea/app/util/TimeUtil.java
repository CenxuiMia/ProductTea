package com.cenxui.tea.app.util;

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

    private static LocalDateTime getLocalDateTimeNow() {
        return LocalDateTime.now(Clock.system(ZoneId.of("UTC+8")));
    }
}
