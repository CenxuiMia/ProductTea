package com.cenxui.tea.app.repositories.order;

import com.cenxui.tea.app.repositories.Repository;

public interface OrderRepository extends Repository {

    Orders getAllOrders();

    Orders getAllOrders(String mail, String time, Integer limit);

    Orders getAllActiveOrders();

    Orders getAllActiveOrders(String mail, String time, Integer limit);

    Orders getAllPaidOrders();

    Orders getAllPaidOrders(String paidTime, Integer limit);

    Orders getAllProcessingOrders();

    Orders getAllProcessingOrders(String processingDate, Integer limit);

    Orders getAllShippedOrders();

    Orders getAllShippedOrders(String shipTime, Integer limit);

    Orders getOrdersByMail(String mail);

    Order getOrdersByMailAndTime(String mail, String time);

    Order addOrder(Order order);

    boolean deleteOrder(String mail, String time);

    Order activeOrder(String mail, String time);

    Order deActiveOrder(String mail, String time);

    Order payOrder(String mail, String time);

    Order payOrder(String mail, String time, String paidTime);

    Order dePayOrder(String mail, String time);

    Order shipOrder(String mail, String time);

    Order shipOrder(String mail, String time, String shippedTime);

    Order deShipOrder(String mail, String time);

//    //todo add order money sum order sum paid sum etc...
//
//    Double getAllPaidOrderIncome();
//
//    Double getAllPaidOrderIncome(String from);
//
//    Double getAllPaidOrderIncome(String from, String to);

}
