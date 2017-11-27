package com.cenxui.tea.app.repositories.order;

import com.cenxui.tea.app.repositories.Repository;

public interface OrderRepository extends Repository {

    OrderResult getAllOrders();

    OrderResult getAllOrdersByLastKey(Integer limit, String mail, String time);

    OrderResult getAllProcessingOrders();

    OrderResult getAllProcessingOrders(Integer limit, String mail, String time);

    OrderResult getAllShippedOrders();

    OrderResult getAllShippedOrders(Integer limit, String mail, String time);

    OrderResult getAllPaidOrders();

    OrderResult getAllPaidOrders(Integer limit, String mail, String time);

    OrderResult getOrdersByMail(String mail);

    Order getOrdersByMailAndTime(String mail, String time);

    Order addOrder(Order order);

    boolean deleteOrder(String mail, String time);

    Order activeOrder(String mail, String time);

    Order deActiveOrder(String mail, String time);

    Order payOrder(String mail, String time);

    Order dePayOrder(String mail, String time);

    Order shipOrder(String mail, String time);

    Order deShipOrder(String mail, String time);

}
