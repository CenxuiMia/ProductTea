package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.cenxui.tea.app.repositories.order.Order;
import org.junit.Test;

import static org.junit.Assert.*;

public class DynamoDBOrderRepositoryTest {
    @Test
    public void getAllOrders() throws Exception {
    }

    @Test
    public void getOrderByTMail() throws Exception {
    }

    @Test
    public void getOrdersByMailAndTime() throws Exception {
    }

    @Test
    public void addOrder() throws Exception {
    }

    @Test
    public void removeOrder() throws Exception {
    }

    @Test
    public void updateOrder() throws Exception {
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
    }

    @Test
    public void shipOrder() throws Exception {
    }

}