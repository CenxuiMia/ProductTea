package com.cenxui.tea.app.application.admin;

import com.cenxui.tea.app.util.Http;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class OrderPathTest {

    private String url;

    @Before
    public void setUp() {
        url = "http://localhost:9000/admin/orders";
    }

    @Test
    public void getOrders() {
        Http.get(url);
    }

}
