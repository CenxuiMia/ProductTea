package com.cenxui.shop.admin.app.application;

import com.cenxui.shop.util.Http;
import org.junit.Before;
import org.junit.Test;

public class ProductPathTest {

    private String url;

    @Before
    public void setUp() {
        url = "http://localhost:9000/admin/product/table";
    }

    @Test
    public void getProducts() {
        Http.get(url);
    }

    @Test
    public void putProduct() {
    }

    @Test
    public void deleteProduct() {
        Http.delete(url+ "/紅茶/美人茶");
    }

}
