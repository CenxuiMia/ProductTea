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

        System.out.println(DynamoDBRepositoryService.getOrderRepository().getAllOrders());
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
            products.add("grean tea: 2");
            products.add("black tea: 5)");

            Order clientOrder = Order.of(
                    null,
                    products,
                    "cexui",
                        1000f,
                    "mia",
                    "0928554033",
                    "aaa",
                    null,
                    null,
                    null,
                    null,
                    null);

            String orderJson = clientOrder.toJson();
            System.out.println(orderJson);

            ObjectMapper mapper = new ObjectMapper();

            Order order = mapper.readValue(orderJson, Order.class);

            DynamoDBRepositoryService.getOrderRepository().addOrder(i + "1@gmail.com", order);
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