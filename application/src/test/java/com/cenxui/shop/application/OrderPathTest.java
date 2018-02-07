package com.cenxui.shop.application;

import com.cenxui.shop.repositories.order.Order;
import com.cenxui.shop.util.Http;
import com.cenxui.shop.util.JsonUtil;
import com.cenxui.shop.web.app.controller.util.Header;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderPathTest {

    private String url;

    @Before
    public void setUp() {
        url = "http://localhost:9001/order";
    }

    @Test
    public void addOrder() {

        Map<String, String> headers3 = new HashMap<>();
        headers3.put(Header.MAIL, "cenxuilin@gmail.com");

        List<String> products = new ArrayList<>();

        products.add("紅顏;經典;2");

        Http.put(url, JsonUtil.mapToJson(OrderUtil.getOrder(products)), headers3);
    }

    @Test
    public void getOrders() {
    }

    @Test
    public void trialOrder() {

        Map<String, String> headers1 = new HashMap<>();
        headers1.put(Header.MAIL, "mia@gmail.com");

        List<String> products = new ArrayList<>();

        products.add("紅顏;經典;2");

        Order order = Order.of(null,
                null,
                products ,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "12345",
                null,
                null,
                null,
                "home",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
                );

        Http.post(url, JsonUtil.mapToJson(order));

    }

}
