package com.cenxui.tea.app.application.admin;

import com.cenxui.tea.app.util.Http;
import org.junit.Before;
import org.junit.Test;

public class OrderProcessingPathTest {
    private String url;

    @Before
    public void setUp() {
        url = "http://localhost:9000/admin/order/shipped";
    }

    @Test
    public void getOrders() {
        Http.get(url);
    }
}
