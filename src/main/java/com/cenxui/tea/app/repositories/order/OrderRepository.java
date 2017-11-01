package com.cenxui.tea.app.repositories.order;

import com.cenxui.tea.app.repositories.Repository;

import java.util.List;

public interface OrderRepository extends Repository {

    List<Order> listAllOrders();

    List<Order> listOrderByTimeStamp(String timeStamp);

    Order getOrderById(String id);

    void addOrder(Order order);

    void removeOrder();

    void updateOrder();

}
