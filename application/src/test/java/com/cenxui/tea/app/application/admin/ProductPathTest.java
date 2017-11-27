package com.cenxui.tea.app.application.admin;

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
        url = "http://localhost:9000/admin/product";
    }

    @Test
    public void getProducts() {
        Http.get(url);
    }

    @Test
    public void putProduct() {
        Product product = Product.of
                ("烏龍茶", "翠玉", "Ａ",
                        "Ａ","Ａ","A",
                new ArrayList<>(),1400F,"Ａ");

        String body = JsonUtil.mapToJson(product);

        Http.put(url, body);

    }
}
