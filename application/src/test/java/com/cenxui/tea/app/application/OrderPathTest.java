package com.cenxui.tea.app.application;

import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.services.util.Header;
import com.cenxui.tea.app.util.Http;
import com.cenxui.tea.app.util.JsonUtil;
import com.sun.org.apache.xpath.internal.operations.Or;
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
        for (int i = 0; i<3; i++) {

            Map<String, String> headers1 = new HashMap<>();
            headers1.put(Header.MAIL, "mia@gmail.com");

            Map<String, String> headers2 = new HashMap<>();
            headers2.put(Header.MAIL, "admin@gmail.com");

            Map<String, String> headers3 = new HashMap<>();
            headers3.put(Header.MAIL, "cenxui@gmail.com");

            List<String> products = new ArrayList<>();

            products.add("紅茶;大玉茶;2");


            Http.put(url, JsonUtil.mapToJson(OrderUtil.getOrder(products)), headers1);

            Http.put(url, JsonUtil.mapToJson(OrderUtil.getOrder(products)), headers2);

            Http.put(url, JsonUtil.mapToJson(OrderUtil.getOrder(products)), headers3);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void getOrders() {
        for (int i = 0; i<3; i++) {

            Map<String, String> headers1 = new HashMap<>();
            headers1.put(Header.MAIL, "mia@gmail.com");

            Map<String, String> headers2 = new HashMap<>();
            headers2.put(Header.MAIL, "admin@gmail.com");

            Map<String, String> headers3 = new HashMap<>();
            headers3.put(Header.MAIL, "cenxui@gmail.com");


            Http.get(url, headers1);

            Http.get(url, headers2);

            Http.get(url, headers3);
        }

    }

    @Test
    public void trialOrder() {

        Map<String, String> headers1 = new HashMap<>();
        headers1.put(Header.MAIL, "mia@gmail.com");

        List<String> products = new ArrayList<>();

        products.add("紅茶;大玉茶;2");

        Order order = Order.of(null,
                null,
                products ,
                null,
                null,
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
