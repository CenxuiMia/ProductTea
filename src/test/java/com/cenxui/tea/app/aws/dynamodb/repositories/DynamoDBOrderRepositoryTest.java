package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.cenxui.tea.app.repositories.order.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DynamoDBOrderRepositoryTest {

    @Test
    public void getAllOrders() throws Exception {
        DynamoDBRepositoryService.getOrderRepository().getAllOrders().forEach(
                System.out::println
        );
    }

    @Test
    public void testMoney() {
        System.out.println(2*234 +5*124);
    }

    @Test
    public void getOrderByTMail() throws Exception {
    }

    @Test
    public void getOrdersByMailAndTime() throws Exception {
    }

    @Test
    public void addOrder() throws Exception {
        for (int i = 0; i< 5; i++) {
            List<String> products = new ArrayList<>();
            products.add("green tea;1;2");
            products.add("black tea;1;5");

            Order clientOrder = Order.of(
                    null,
                    products,
                    "cexui",
                    null,
                    "mia",
                    "0928554033",
                    "aaa",
                    null,
                    null,
                    null,
                    null,
                    null);

            String orderJson = clientOrder.toJson();

            ObjectMapper mapper = new ObjectMapper();

            Order order = mapper.readValue(orderJson, Order.class);

            DynamoDBRepositoryService.getOrderRepository().addOrder("cenxui" + i + "@gmail.com", order);
        }

    }

    @Test
    public void removeOrder() throws Exception {
    }

    @Test
    public void activeOrder() {
        Order order = DynamoDBRepositoryService.
                getOrderRepository().
                activeOrder("cenxuilin@gmail.com", "2017-11-16T10:53:48");
        System.out.println(order);
    }

    @Test
    public void deActiveOrder() throws Exception {
        Order order = DynamoDBRepositoryService.
                getOrderRepository().
                deActiveOrder("cenxuilin@gmail.com", "2017-11-16T10:53:48");
        System.out.println(order);
    }

    @Test
    public void payOrder() throws Exception {
        Order order = DynamoDBRepositoryService.
                getOrderRepository().
                payOrder("cenxuilin@gmail.com", "2017-11-16T10:53:48");
        System.out.println(order);
    }

    @Test
    public void dePayOrder() throws Exception {
        Order order = DynamoDBRepositoryService.
                getOrderRepository().
                dePayOrder("cenxuilin@gmail.com", "2017-11-16T10:53:48");
        System.out.println(order);
    }


    @Test
    public void shipOrder() throws Exception {
        Order order = DynamoDBRepositoryService.
                getOrderRepository().
                shipOrder("cenxuilin@gmail.com", "2017-11-16T10:53:48");
        System.out.println(order);
    }

    @Test
    public void deShipOrder() {
        Order order = DynamoDBRepositoryService.
                getOrderRepository().
                deShipOrder("cenxuilin@gmail.com", "2017-11-16T10:53:48");
        System.out.println(order);
    }

}