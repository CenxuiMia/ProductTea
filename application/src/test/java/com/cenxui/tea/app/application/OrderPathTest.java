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
//        List<String> products = new ArrayList<>();
//
//        String good = "花茶;大玉茶;10";
//        products.add(good);
//
//        Order order = Order.of(
//                null, products, "黃盈盈",
//                null, null, "mia","09283399","airbus",
//                "abc","good", null,null,null,null
//        );

        for (int i = 0; i<3; i++) {

            Map<String, String> headers1 = new HashMap<>();
            headers1.put(Header.MAIL, "mia@gmail.com");

            Map<String, String> headers2 = new HashMap<>();
            headers2.put(Header.MAIL, "admin@gmail.com");

            Map<String, String> headers3 = new HashMap<>();
            headers2.put(Header.MAIL, "cenxui@gmail.com");

            String body1 = "{\"purchaser\":\"黃盈盈\",\"mail\":\"帥哥林\",\"phone\":\"0900111222\",\"receiver\":\"帥哥林\",\"shippingWay\":\"超商取貨\",\"shippingAddress\":\"A市B區C路DFG\",\"products\":[\"花茶;大玉茶;2\"],\"comment\":\"none\"}";

            String body2 = "{\"purchaser\":\"黃盈盈\",\"mail\":\"大流士\",\"phone\":\"0900111222\",\"receiver\":\"大流士\",\"shippingWay\":\"超商取貨\",\"shippingAddress\":\"A市B區C路DFG\",\"products\":[\"美茶;大玉茶;2\"],\"comment\":\"none\"}";

            String body3 = "{\"purchaser\":\"呱呱\",\"mail\":\"大流士\",\"phone\":\"0900111222\",\"receiver\":\"珠珠\",\"shippingWay\":\"超商取貨\",\"shippingAddress\":\"A市B區C路DFG\",\"products\":[\"美茶;大玉茶;2\"],\"comment\":\"none\"}";


            Http.put(url, body1, headers1);

            Http.put(url, body2, headers2);

            Http.put(url, body3, headers3);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void addOrder2() {
        Map<String, String> headers1 = new HashMap<>();
        headers1.put(Header.MAIL, "mia@gmail.com");
        String body1 = "{\"purchaser\":\"aaa\",\"phone\":\"0900\",\"receiver\":\"hhh\",\"shippingWay\":\"超商取貨\",\"shippingAddress\":\"tt\",\"products\":[\"花茶;大玉茶;1\"],\"comment\":\"\"}";

        try {
            System.out.println(JsonUtil.mapToOrder(body1));
        } catch (Exception e) {
            e.printStackTrace();
        }


//        String body2 = "{\"purchaser\":\"尿尿小童\",\"phone\":\"09\",\"receiver\":\"勳勳貝貝\",\"shippingWay\":\"宅配\",\"shippingAddress\":\"全家就是你家\",\"products\":[\"綠茶;翠玉;1\",\"烏龍茶;大玉美茶;4\"],\"comment\":\"尿尿小童94勳勳\"}";
//        String body3 = "{\"purchaser\":\"呱呱\",\"phone\":\"0900111222\",\"receiver\":\"珠珠\",\"shippingWay\":\"超商取貨\",\"shippingAddress\":\"A市B區C路DFG\",\"products\":[\"美茶;大玉茶;2\"],\"comment\":\"none\"}";
//
//
        Http.put(url, body1, headers1);

    }

}
