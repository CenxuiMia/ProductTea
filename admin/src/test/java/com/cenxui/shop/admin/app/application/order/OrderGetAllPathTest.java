package com.cenxui.shop.admin.app.application.order;

import com.cenxui.shop.admin.app.util.*;
import com.cenxui.shop.repositories.order.OrderKey;
import com.cenxui.shop.repositories.order.OrderPaidLastKey;
import com.cenxui.shop.repositories.order.OrderProcessingLastKey;
import com.cenxui.shop.repositories.order.OrderShippedLastKey;

import com.cenxui.shop.util.Http;
import org.junit.Test;

public class OrderGetAllPathTest {
    private String url = "http://localhost:9000";

    @Test
    public void getAllOrder() {

        try {

            OrderKey orderKey = null;
            do {
                System.out.println("================start query ==================");

                String body;
                if (orderKey == null) {
                    body = Http.getResult(url + Path.ORDER_ALL);
                }else {
                    body = Http.getResult(url + Path.ORDER_ALL +
                            "/" + orderKey.getMail()+ "/" +  orderKey.getOrderDateTime() + "/" + "2");
                }

                OrderTestResult orders = JsonTestUtil.mapToOrderTestResult(body);
                orders.getOrders().forEach(System.out::println);
                orderKey = orders.getLastKey();

                System.out.println("================   end   ======================");

            }while (orderKey != null);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void getAllActiveOrder() {
        try {

            OrderKey orderKey = null;
            do {
                System.out.println("================start query ==================");

                String body;
                if (orderKey == null) {
                    body = Http.getResult(url + Path.ORDER_ALL_ACTIVE);
                }else {
                    body = Http.getResult(url + Path.ORDER_ALL_ACTIVE +
                            "/" + orderKey.getMail()+ "/" +  orderKey.getOrderDateTime() + "/" + "2");
                }

                OrderTestResult orders = JsonTestUtil.mapToOrderTestResult(body);
                orders.getOrders().forEach(System.out::println);
                orderKey = orders.getLastKey();

                System.out.println("================   end   ======================");

            }while (orderKey != null);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllPaidOrder() {
        try {

            OrderPaidLastKey orderKey = null;
            do {
                System.out.println("================start query ==================");

                String body;
                if (orderKey == null) {
                    body = Http.getResult(url + Path.ORDER_PAID);
                }else {
                    body = Http.getResult(url + Path.ORDER_PAID +
                            "/" + orderKey.getPaidDate() + "/" + orderKey.getPaidTime()+
                            "/" + orderKey.getMail()+ "/" +  orderKey.getOrderDateTime() + "/" + "2");
                }

                PaidOrderTestResult orders = JsonTestUtil.mapToPaidOrderTest(body);
                orders.getOrders().forEach(System.out::println);
                orderKey = orders.getLastKey();

                System.out.println("================   end   ======================");

            }while (orderKey != null);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getProcessingOrder() {
        try {

            OrderProcessingLastKey orderKey = null;
            do {
                System.out.println("================start query ==================");

                String body;
                if (orderKey == null) {
                    body = Http.getResult(url + Path.ORDER_PROCESSING);
                }else {
                    body = Http.getResult(url + Path.ORDER_PROCESSING +
                            "/" + orderKey.getProcessingDate() + "/" + orderKey.getOwner()+
                            "/" + orderKey.getMail()+ "/" +  orderKey.getOrderDateTime() + "/" + "2");
                }

                ProcessingOrderTestResult orders = JsonTestUtil.mapToProcessingOrderTestResult(body);
                orders.getOrders().forEach(System.out::println);
                orderKey = orders.getLastKey();

                System.out.println("================   end   ======================");

            }while (orderKey != null);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getShippedOrder() {
        try {

            OrderShippedLastKey orderKey = null;
            do {
                System.out.println("================start query ==================");

                String body;
                if (orderKey == null) {
                    body = Http.getResult(url + Path.ORDER_SHIPPED);
                }else {
                    body = Http.getResult(url + Path.ORDER_SHIPPED +
                            "/" + orderKey.getShippedDate() + "/" + orderKey.getShippedTime()+
                            "/" + orderKey.getMail()+ "/" +  orderKey.getOrderDateTime() + "/" + "2");
                }

                ShippedOrderTestResult orders = JsonTestUtil.mapToShippedOrderTestResult(body);
                orders.getOrders().forEach(System.out::println);
                orderKey = orders.getLastKey();
                System.out.println(orderKey);
                System.out.println("================   end   ======================");

            }while (orderKey != null);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
