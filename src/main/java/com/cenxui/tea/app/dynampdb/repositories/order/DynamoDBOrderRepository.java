package com.cenxui.tea.app.dynampdb.repositories.order;

import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderRepository;

import java.util.List;

public class DynamoDBOrderRepository implements OrderRepository {
    @Override
    public List<Order> listAllOrders() {
        return null;
    }

    @Override
    public List<Order> listOrderByTimeStamp(String timeStamp) {
        return null;
    }

    @Override
    public Order getOrderById(String id) {
        return null;
    }

    @Override
    public void addOrder(Order order) {

    }

    @Override
    public void removeOrder() {

    }

    @Override
    public void updateOrder() {

    }
}
