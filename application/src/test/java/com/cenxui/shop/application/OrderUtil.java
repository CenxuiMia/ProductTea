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
                "0928554033",
                null,
                null,
                null,
                null,
                "account",
                "00812345",
                null,
                null,
                null,
                "華盈",
                "0972858256",
                "shop",
                "alert(1)",
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
