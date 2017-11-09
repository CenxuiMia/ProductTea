package com.cenxui.tea.app.repositories.order;

import com.cenxui.tea.app.repositories.Repository;

import java.util.List;

public interface OrderRepository extends Repository {

    List<Order> getAllOrders();

    List<Order> getOrderByTMail(String mail);

    Order getOrdersByMailAndTime(String mail, String time);

    boolean addOrder(Order order);

    boolean removeOrder();

    boolean updateOrder();

}
