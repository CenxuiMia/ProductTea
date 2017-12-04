package com.cenxui.tea.app.application.admin;

import com.cenxui.tea.app.repositories.order.*;
import com.cenxui.tea.app.services.util.Param;
import com.cenxui.tea.app.services.util.Path;
import com.cenxui.tea.app.util.*;
import org.junit.Test;

public class OrderGetAllPathTest {
    private String url = "http://localhost:9000";

    @Test
    public void getAllOrder() {
///todo bug
        try {

            OrderKey orderKey = null;
            do {
                System.out.println("================start query ==================");

                String body;
                if (orderKey == null) {
                    body = Http.getResult(url + Path.Admin.ORDER_ALL);
                }else {
                    body = Http.getResult(url + Path.Admin.ORDER_ALL +
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
                    body = Http.getResult(url + Path.Admin.ORDER_ALL_ACTIVE);
                }else {
                    body = Http.getResult(url + Path.Admin.ORDER_ALL_ACTIVE +
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
                    body = Http.getResult(url + Path.Admin.ORDER_PAID);
                }else {
                    body = Http.getResult(url + Path.Admin.ORDER_PAID +
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
                    body = Http.getResult(url + Path.Admin.ORDER_PROCESSING);
                }else {
                    body = Http.getResult(url + Path.Admin.ORDER_PROCESSING +
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

            //todo bug fixed
            OrderShippedLastKey orderKey = null;
            do {
                System.out.println("================start query ==================");

                String body;
                if (orderKey == null) {
                    body = Http.getResult(url + Path.Admin.ORDER_SHIPPED);
                }else {
                    body = Http.getResult(url + Path.Admin.ORDER_SHIPPED +
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
