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
        url = "http://localhost:9000/admin/order/table";
    }

    @Test
    public void getOrders() {
        Http.get(url);
    }

    @Test
    public void activeOrder() {
        Http.post(url + "/active/5cenxui@gmail.com/2017-11-29T17:19:51");
    }

    @Test
    public void deactiveOrder() {
        Http.post(url + "/deactive/5cenxui@gmail.com/2017-11-29T17:19:51");

    }

    @Test
    public void paidOrder() {
        Http.post(url + "/pay/5cenxui@gmail.com/2017-11-29T17:19:51");
    }

    @Test
    public void dePaidOrder() {
        Http.post(url + "/depay/5cenxui@gmail.com/2017-11-29T17:19:51");

    }

    @Test
    public void shippedOrder() {
        Http.post(url + "/ship/5cenxui@gmail.com/2017-11-29T17:19:51");
    }

    @Test
    public void deShippedorder() {
        Http.post(url + "/deship/5cenxui@gmail.com/2017-11-29T17:19:51");

    }

}
