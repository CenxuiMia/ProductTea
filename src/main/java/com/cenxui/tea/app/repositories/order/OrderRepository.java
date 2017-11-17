package com.cenxui.tea.app.repositories.order;

import com.cenxui.tea.app.repositories.Repository;

import java.util.List;

public interface OrderRepository extends Repository {

    List<Order> getAllOrders();

    List<Order> getOrderByTMail(String mail);

    Order getOrdersByMailAndTime(String mail, String time);

    boolean addOrder(Order order);

    boolean removeOrder(String mail, String time);

    Order activeOrder(String mail, String time);

    Order deActiveOrder(String mail, String time);

    Order payOrder(String mail, String time);

    Order dePayOrder(String mail, String time);

    Order shipOrder(String mail, String time);

    Order deShipOrder(String mail, String time);

}
