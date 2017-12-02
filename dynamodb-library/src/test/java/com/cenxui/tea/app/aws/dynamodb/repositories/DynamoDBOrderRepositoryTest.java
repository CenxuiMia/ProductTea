package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderRepository;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DynamoDBOrderRepositoryTest {
    private OrderRepository orderRepository = DynamoDBRepositoryService
            .getOrderRepository(
                    Config.REGION,
                    Config.ORDER_TABLE,
                    Config.ORDER_PAID_INDEX,
                    Config.ORDER_PROCESSING_INDEX,
                    Config.ORDER_SHIPPED_INDEX,
                    Config.PRODUCT_TABLE);


    @Test
    public void getAllOrdersByLastKey() throws Exception {
    }

    @Test
    public void getAllProcessingOrders() throws Exception {
    }

    @Test
    public void getAllShippedOrders() throws Exception {
    }

    @Test
    public void getAllPaidOrders() throws Exception {
        orderRepository.getAllPaidOrders().getOrders().forEach(System.out::println);
    }

    @Test
    public void getOrdersByMail() throws Exception {
        orderRepository.getOrdersByMail("0cenxui@gmail.com").getOrders().forEach(System.out::println);
    }

    @Test
    public void getAllOrders() throws Exception {
        orderRepository.getAllOrders().getOrders().forEach(System.out::println);
    }

    @Test
    public void getAllActiveOrders() throws Exception {
        orderRepository.getAllActiveOrders().getOrders().forEach(System.out::println);
    }

    @Test
    public void getAllProcessingOrders1() throws Exception {
    }

    @Test
    public void getAllShippedOrders1() throws Exception {
    }

    @Test
    public void getAllPaidOrders1() throws Exception {
    }

    @Test
    public void getOrdersByMailAndTime() throws Exception {
//        mtlisa42@gmail.com 2017-12-01T15:14:51
        orderRepository.getOrdersByMailAndTime("mtlisa42@gmail.com", "2017-12-01T15:14:51").getProducts().forEach(System.out::println);
    }

    @Test
    public void addOrder() throws Exception {}

    @Test
    public void deleteOrder() throws Exception {
    }

    @Test
    public void activeOrder() throws Exception {
        System.out.println(orderRepository.activeOrder("5cenxui@gmail.com", "2017-11-29T17:19:51"));

    }

    @Test
    public void deactiveOrder() throws Exception {
        System.out.println(orderRepository.deActiveOrder("5cenxui@gmail.com", "2017-11-29T17:19:51"));

    }

    @Test
    public void payOrder() throws Exception {
        System.out.println(orderRepository.payOrder("5cenxui@gmail.com", "2017-11-29T17:19:51"));

    }

    @Test
    public void depayOrder() throws Exception {
        System.out.println(orderRepository.dePayOrder("5cenxui@gmail.com", "2017-11-29T17:19:51"));

    }

    @Test
    public void shipOrder() throws Exception {
        System.out.println(orderRepository.shipOrder("5cenxui@gmail.com", "2017-11-29T17:19:51"));

    }

    @Test
    public void deshipOrder() throws Exception {
        System.out.println(orderRepository.deShipOrder("5cenxui@gmail.com", "2017-11-29T17:19:51"));

    }

    @Test
    public void getAllCashReport() {
        System.out.println(orderRepository.getCashAllReport());
    }


}