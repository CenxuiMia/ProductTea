package com.cenxui.shop.application;

import com.cenxui.shop.util.Http;
import com.cenxui.shop.web.app.controller.util.Header;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PointPathTest {
    private String url;

    @Before
    public void setUp() {
        url = "http://localhost:9001/point";
    }

    @Test
    public void getPoint() {

        Map<String, String> headers3 = new HashMap<>();
        headers3.put(Header.MAIL, "cenxuilin@gmail.com");

        Http.get(url , headers3);
    }

}
