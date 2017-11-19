package com.cenxui.tea.app.repositories.order;

import com.cenxui.tea.app.repositories.Repository;

import java.util.List;

public interface OrderRepository<Key> extends Repository {

    OrderResult getAllOrders();

    OrderResult getAllProcessingOrders();

    OrderResult getAllShippedOrders();

    OrderResult getAllPaidOrders();

    Order getOrdersByMailAndTime(String mail, String time);

    Order addOrder(String mail, Order clientOrder);

    boolean removeOrder(String mail, String time);

    Order activeOrder(String mail, String time);

    Order deActiveOrder(String mail, String time);

    Order payOrder(String mail, String time);

    Order dePayOrder(String mail, String time);

    Order shipOrder(String mail, String time);

    Order deShipOrder(String mail, String time);

}
