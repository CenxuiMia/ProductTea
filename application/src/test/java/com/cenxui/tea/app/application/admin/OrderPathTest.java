package com.cenxui.tea.app.application.admin;

import com.cenxui.tea.app.util.Http;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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
    public void getOrder() {
        Http.get("http://localhost:9000/admin/order/table/mtlisa42@gmail.com/2017-12-01T15:14:51");
    }

    @Test
    public void activeOrder() {
        Http.post(url + "/active/mia@gmail.com/2017-12-02T20:56:02");
    }

    @Test
    public void deactiveOrder() {
        Http.post(url + "/deactive/mia@gmail.com/2017-12-02T20:56:02");

    }
    @Test
    public void paidOrder() {
        Http.post(url + "/pay/mia@gmail.com/2017-12-02T20:56:02");
    }

    @Test
    public void dePaidOrder() {
        Http.post(url + "/depay/mia@gmail.com/2017-12-02T20:56:02");

    }

    @Test
    public void shippedOrder() {


        Http.post(url + "/ship/mia@gmail.com/2017-12-02T20:56:02");
    }

    @Test
    public void deShippedorder() {
        Http.post(url + "/deship/mia@gmail.com/2017-12-02T20:56:02");

    }



}
