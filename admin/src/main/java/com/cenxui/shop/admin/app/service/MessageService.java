package com.cenxui.shop.admin.app.service;

import com.cenxui.shop.repositories.order.Order;

public interface MessageService {
    void sendShippedOrderMessage(Order order);
}
