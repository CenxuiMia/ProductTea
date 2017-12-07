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
        Product product1 = Product.of
                ("花茶", "大玉冰茶", "Ａ",
                        "Ａ","Ａ","A",
                new ArrayList<>(), 4400F, 6000F, "A");



        Product product2 = Product.of
                ("紅茶", "大玉茶", "Ａ",
                        "Ａ","Ａ","A",
                        new ArrayList<>(), 2200F,2400F, "A");

        Product product3 = Product.of
                ("花茶", "大玉冰茶", "Ａ",
                        "Ａ","Ａ","A",
                        new ArrayList<>(), 1400F, 2000F, "A");



        Product product4 = Product.of
                ("菊茶", "翠玉茶", "Ａ",
                        "Ａ","Ａ","A",
                        new ArrayList<>(), 2200F,2400F, "A");

        Product product5 = Product.of
                ("黑茶", "大玉冰茶", "Ａ",
                        "Ａ","Ａ","A",
                        new ArrayList<>(), 4400F, 6000F, "A");



        Product product6 = Product.of
                ("黃茶", "大玉茶", "Ａ",
                        "Ａ","Ａ","A",
                        new ArrayList<>(), 2200F,2400F, "A");

        Product product7 = Product.of
                ("蘭茶", "大玉冰茶", "Ａ",
                        "Ａ","Ａ","A",
                        new ArrayList<>(), 1400F, 2000F, "A");



        Product product8 = Product.of
                ("美茶", "翠玉茶", "Ａ",
                        "Ａ","Ａ","A",
                        new ArrayList<>(), 2200F,2400F, "A");


        Http.put(url, JsonUtil.mapToJson(product1));
        Http.put(url, JsonUtil.mapToJson(product2));
        Http.put(url, JsonUtil.mapToJson(product3));
        Http.put(url, JsonUtil.mapToJson(product4));
        Http.put(url, JsonUtil.mapToJson(product5));
        Http.put(url, JsonUtil.mapToJson(product6));
        Http.put(url, JsonUtil.mapToJson(product7));
        Http.put(url, JsonUtil.mapToJson(product8));
    }
}
