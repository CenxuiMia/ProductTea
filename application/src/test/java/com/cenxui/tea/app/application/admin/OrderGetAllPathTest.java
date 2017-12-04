package com.cenxui.tea.app.application.admin;

import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderKey;
import com.cenxui.tea.app.repositories.order.Orders;
import com.cenxui.tea.app.services.util.Param;
import com.cenxui.tea.app.services.util.Path;
import com.cenxui.tea.app.util.Http;
import com.cenxui.tea.app.util.JsonTestUtil;
import com.cenxui.tea.app.util.JsonUtil;
import com.cenxui.tea.app.util.OrderTestResult;
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

    }

    @Test
    public void getProcessingOrder() {

    }

    @Test
    public void getShippedOrder() {

    }

}
