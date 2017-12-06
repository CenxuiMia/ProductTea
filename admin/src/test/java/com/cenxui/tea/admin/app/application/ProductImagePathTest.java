package com.cenxui.tea.admin.app.application;

import com.cenxui.tea.app.image.ProductImage;
import com.cenxui.tea.app.util.Http;
import com.cenxui.tea.app.util.JsonUtil;
import org.junit.Test;

public class ProductImagePathTest {

    private String url = "http://localhost:9000/com.cenxui.app.admin/product/image";

    @Test
    public void upload() {
        ProductImage image = ProductImage.of("/Users/cenxui/Downloads/12166549_911034718984522_1989228322_n.jpg");

        Http.put(url, JsonUtil.mapToJson(image));
    }
}
