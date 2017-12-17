package com.cenxui.tea.admin.app.application;

import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.util.Http;
import com.cenxui.tea.app.util.JsonUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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
