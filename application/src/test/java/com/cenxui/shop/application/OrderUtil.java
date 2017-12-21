package com.cenxui.shop.application;

import com.cenxui.shop.repositories.order.Order;

import java.util.List;

public class OrderUtil {
    public static Order getOrder(List<String> products) {
        return Order.of(
                null,
                null,
                products,
                "Cenxui",
                null,
                "匯款",
                "華盈",
                "0192838",
                "shop",
                "AADLLD",
                "Hello",
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }


}
