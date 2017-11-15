package com.cenxui.tea.app.aws.dynamodb.local.repositories.util;

import com.cenxui.tea.app.repositories.order.Order;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TestData {
    public static List<Order> getOrders() {
        List<String> products =
                Arrays.asList("green tea = 10",
                        "black tea = 11",
                        "lol tea = 10"
                );


        String paidDate = LocalDate.now().toString();
        String shipDate = LocalDate.now().toString();

        return Arrays.asList(
//                Order.of(
//                        "abc@gmail.com",
//                        products,
//                        "purchaser",
//                        "123",
//                        "taipei",
//                        "acvb",
//                        paidDate,
//                        shipDate,
//                        true
//
//                ),
//                Order.of(
//                        "mia@gmail.com",
//                        products,
//                        "purchaser",
//                        "321",
//                        "taipei",
//                        "acvb",
//                        "2017-11-04",
//                        "2017-11-04",
//                        true
//                ),
//                Order.of(
//                        "123@gmail.com",
//                        products,
//                        "purchaser",
//                        "123",
//                        "taipei",
//                        "acvb",
//                        "2017-11-05",
//                        "2017-11-05",
//                        true
//                )
//                ,  Order.of(
//                        "cp9@gmail.com",
//                        products,
//                        "purchaser",
//                        "467",
//                        "taipei",
//                        "acvb",
//                        "2017-11-06",
//                        "2017-11-06",
//                        true
//                )

        );
    }
}
