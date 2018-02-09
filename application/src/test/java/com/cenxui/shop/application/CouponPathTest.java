package com.cenxui.shop.application;

import com.cenxui.shop.util.Http;
import com.cenxui.shop.util.JsonUtil;
import com.cenxui.shop.web.app.controller.util.Header;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponPathTest {

    private String url;

    @Before
    public void setUp() {
        url = "http://localhost:9001/coupon";
    }

    @Test
    public void getActiveCoupon() {

        Map<String, String> headers3 = new HashMap<>();
        headers3.put(Header.MAIL, "cenxui@gmail.com");

        Http.get(url + "/active", headers3);
        Http.get(url + "/more", headers3);
    }

    @Test
    public void getSignUpCoupon() {
        Map<String, String> headers3 = new HashMap<>();
        headers3.put(Header.MAIL, "cenxui@gmail.com");

        Http.put(url + "/more/signUp","", headers3);
    }

}
