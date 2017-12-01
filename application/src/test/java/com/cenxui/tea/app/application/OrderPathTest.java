package com.cenxui.tea.app.application;

import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.services.util.Header;
import com.cenxui.tea.app.util.Http;
import com.cenxui.tea.app.util.JsonUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderPathTest {

    private String url;

    @Before
    public void setUp() {
        url = "http://localhost:9000/order";
    }

    @Test
    public void addOrder() {
        List<String> products = new ArrayList<>();

        String good = "花茶;大玉茶;10";
        products.add(good);

        Order order = Order.of(
                null, products, "cenxui",
                0F, "mia","09283399","airbus",
                "abc","good", null,null,null,null
        );

        Map<String, String> headers = new HashMap<>();
        headers.put(Header.MAIL, "cenxui@gmail.com");

        Http.put(url, JsonUtil.mapToJson(order), headers);
    }

}
