package com.cenxui.tea.app.application;

import com.cenxui.tea.app.util.Http;
import org.junit.Before;
import org.junit.Test;

public class ProductPathTest {

    private String url;

    @Before
    public void setUp() {
        url = "http://localhost:9001/product";
    }

    @Test
    public void getAllProducts() {
        Http.get(url);
    }

    @Test
    public void getProduct() {
        Http.get(url + "/黃茶/大玉茶");
    }
}
