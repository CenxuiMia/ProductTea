package com.cenxui.shop.admin.app.application.order;

import com.cenxui.shop.util.Http;
import org.junit.Test;

public class OrderGetPathTest {

    private String url = "http://localhost:9000/admin/order";

    @Test
    public void getOrderByMailAndTime() {


    }

    @Test
    public void getOrderByBankInformation() {
        Http.get(url + "/bank/12345");
    }
}
