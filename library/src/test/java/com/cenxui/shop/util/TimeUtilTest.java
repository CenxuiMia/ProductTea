package com.cenxui.shop.util;

import org.junit.Test;

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

}