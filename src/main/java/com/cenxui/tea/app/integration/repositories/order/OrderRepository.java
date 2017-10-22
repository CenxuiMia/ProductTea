package com.cenxui.tea.app.integration.repositories.order;

import com.cenxui.tea.app.integration.repositories.Repository;
import com.sun.tools.corba.se.idl.constExpr.Or;

import java.util.List;

public interface OrderRepository extends Repository {

    List<Order> listAllOrders();

    List<Order> listOrderByTimeStamp(String timeStamp);

    Order getOrderById(String id);

    void addOrder(Order order);

    void removeOrder();

    void updateOrder();

}
