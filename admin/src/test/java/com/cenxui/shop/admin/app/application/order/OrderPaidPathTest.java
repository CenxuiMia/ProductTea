package com.cenxui.shop.admin.app.application.order;

import com.cenxui.shop.util.Http;
import org.junit.Before;
import org.junit.Test;

public class OrderPaidPathTest {
    private String url;

    @Before
    public void setUp() {
        url = "http://localhost:9000/com.cenxui.app.admin/order/paid";
    }

    @Test
    public void getOrders() {
        Http.get(url);
    }

}
