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
        Product product1 = Product.of
                ("花茶", "大玉冰茶", "Ａ",
                        "Ａ","Ａ","A",
                new ArrayList<>(),4400F,"NT", "A");



        Product product2 = Product.of
                ("美茶", "大玉茶", "Ａ",
                        "Ａ","Ａ","A",
                        new ArrayList<>(),2400F,"NT", "A");

        Product product3 = Product.of
                ("花茶", "大玉茶", "Ａ",
                        "Ａ","Ａ","A",
                        new ArrayList<>(),6400F,"NT", "A");

        Product product4 = Product.of
                ("綠茶", "美玉茶", "Ａ",
                        "Ａ","Ａ","A",
                        new ArrayList<>(),400F,"NT", "A");

        Http.put(url, JsonUtil.mapToJson(product1));
        Http.put(url, JsonUtil.mapToJson(product2));

        Http.put(url, JsonUtil.mapToJson(product3));
        Http.put(url, JsonUtil.mapToJson(product4));

    }
}
