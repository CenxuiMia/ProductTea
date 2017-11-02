package com.cenxui.tea.app.repositories.order;

import com.cenxui.tea.app.repositories.Repository;

import java.util.List;

public interface OrderRepository extends Repository {

    List<Order> listAllOrders();

    List<Order> listOrderByTMail(String mail);

    Order getOrdersByMailAndTime(String mail, String time);

    void addOrder(Order order);

    void removeOrder();

    void updateOrder();

}
