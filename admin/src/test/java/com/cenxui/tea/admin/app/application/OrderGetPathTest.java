package com.cenxui.tea.admin.app.application;

import com.cenxui.tea.app.util.Http;
import org.junit.Test;

public class OrderGetPathTest {

    private String url = "http://localhost:9000/admin/order/table";

    @Test
    public void getOrderByMailAndTime() {
        Http.get(url + "/cenxui@gmail.com/2017-12-07T15:35:52");

    }
}
