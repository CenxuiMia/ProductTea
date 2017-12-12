package com.cenxui.tea.app.util;

import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;

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