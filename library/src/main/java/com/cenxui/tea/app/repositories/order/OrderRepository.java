package com.cenxui.tea.app.repositories.order;

import com.cenxui.tea.app.repositories.order.report.CashReport;
import com.cenxui.tea.app.repositories.Repository;

public interface OrderRepository extends Repository {

    Orders getAllOrders();

    Orders getAllOrders(String mail, String time, Integer limit);

    Orders getAllActiveOrders();

    Orders getAllActiveOrders(String mail, String time, Integer limit);

    Orders getAllPaidOrders();

    Orders getAllPaidOrders(OrderPaidLastKey orderPaidLastKey, Integer limit);

    Orders getAllProcessingOrders();

    Orders getAllProcessingOrders(OrderProcessingLastKey orderProcessingLastKey, Integer limit);

    Orders getAllShippedOrders();

    Orders getAllShippedOrders(OrderShippedLastKey orderShippedLastKey, Integer limit);

    Orders getOrdersByMail(String mail);

    Order getOrdersByMailAndTime(String mail, String time);

    Order addOrder(Order order);

    boolean deleteOrder(String mail, String time);

    Order activeOrder(String mail, String time);

    Order deActiveOrder(String mail, String time);

    Order payOrder(String mail, String time);

    Order payOrder(String mail, String time, String paidDate, String paidTime);

    Order dePayOrder(String mail, String time);

    Order shipOrder(String mail, String time);

    Order shipOrder(String mail, String time, String paidDate, String shippedTime);

    Order deShipOrder(String mail, String time);

    CashReport getAllCashReport();

    CashReport getDailyCashReport(String paidDate);

    CashReport getRangeCashReport(String fromPaidDate, String toPaidDate);


}
