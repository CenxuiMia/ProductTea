package com.cenxui.shop.aws.dynamodb.repositories;

import com.cenxui.shop.repositories.order.*;
import com.cenxui.tea.app.repositories.order.*;
import org.junit.Test;

public class DynamoDBOrderBaseRepositoryTest {
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


        OrderKey orderKey = null;

        do {
            Orders orders;
            if (orderKey == null) {
                orders  = orderRepository.getAllOrders();
            }else {
                orders = orderRepository.getAllOrders(orderKey.getMail(), orderKey.getOrderDateTime(), 2);
            }

            System.out.println("=========================== first query =======================");
            System.out.println("start key" + orderKey);
            orders.getOrders().forEach(System.out::println);

            System.out.println("=========================== end =======================");

            orderKey = ((OrderKey)orders.getLastKey());


        }while (orderKey !=null);

    }

    @Test
    public void getAllProcessingOrders() throws Exception {

        OrderProcessingLastKey orderKey = null;

        do {
            Orders orders;
            if (orderKey == null) {
                orders  = orderRepository.getAllProcessingOrders();
            }else {
                orders = orderRepository.getAllProcessingOrders(orderKey, 2);
            }

            System.out.println("=========================== first query =======================");
            System.out.println("start key" + orderKey);
            orders.getOrders().forEach(System.out::println);

            System.out.println("=========================== end =======================");

            orderKey = ((OrderProcessingLastKey)orders.getLastKey());


        }while (orderKey !=null);

    }


    @Test
    public void getAllShippedOrders() throws Exception {

        OrderShippedLastKey orderKey = null;

        do {
            Orders orders;
            if (orderKey == null) {
                orders  = orderRepository.getAllShippedOrders();
            }else {
                orders = orderRepository.getAllShippedOrders(orderKey, 2);
            }

            System.out.println("=========================== first query =======================");
            System.out.println("start key" + orderKey);
            orders.getOrders().forEach(System.out::println);

            System.out.println("=========================== end =======================");

            orderKey = ((OrderShippedLastKey)orders.getLastKey());


        }while (orderKey !=null);

    }

    @Test
    public void getAllPaidOrders() throws Exception {

        OrderPaidLastKey orderKey = null;

        do {
            Orders orders;
            if (orderKey == null) {
                orders  = orderRepository.getAllPaidOrders();
            }else {
                orders = orderRepository.getAllPaidOrders(orderKey, 2);
            }

            System.out.println("=========================== first query =======================");
            System.out.println("start key" + orderKey);
            orders.getOrders().forEach(System.out::println);

            System.out.println("=========================== end =======================");

            orderKey = ((OrderPaidLastKey)orders.getLastKey());


        }while (orderKey !=null);

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
        OrderKey orderKey = null;

        do {
            Orders orders;
            if (orderKey == null) {
                orders  = orderRepository.getAllActiveOrders();
            }else {
                orders = orderRepository.getAllActiveOrders(orderKey.getMail(), orderKey.getOrderDateTime(), 2);
            }

            System.out.println("=========================== first query =======================");
            System.out.println("start key" + orderKey);
            orders.getOrders().forEach(System.out::println);

            System.out.println("=========================== end =======================");

            orderKey = ((OrderKey)orders.getLastKey());


        }while (orderKey !=null);

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
        System.out.println(orderRepository.getAllCashReport());
    }

    @Test
    public void getDailyCashReport() {
        System.out.println(orderRepository.getDailyCashReport("2017-12-03"));
    }

    @Test
    public void getDailyCashReport2() {
        Double d = orderRepository.getDailyCashReport("2017-12-03").getRevenue() +
                orderRepository.getDailyCashReport("2017-12-04").getRevenue();

        System.out.println(d);
        System.out.println(orderRepository.getRangeCashReport("2017-12-03", "2017-12-04").getRevenue());
        System.out.println(orderRepository.getAllCashReport().getRevenue());
    }

}