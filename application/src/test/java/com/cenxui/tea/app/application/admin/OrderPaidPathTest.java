package com.cenxui.tea.app.application.admin;

import com.cenxui.tea.app.util.Http;
import org.junit.Before;
import org.junit.Test;

public class OrderPaidPathTest {
    private String url;

    @Before
    public void setUp() {
        url = "http://localhost:9000/admin/order/paid";
    }

    @Test
    public void getOrders() {
        Http.get(url);
    }

}
