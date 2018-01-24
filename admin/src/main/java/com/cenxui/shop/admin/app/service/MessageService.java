package com.cenxui.shop.admin.app.service;

import com.cenxui.shop.repositories.order.Order;

public interface MessageService {
    void sendPayOrderMessage(Order paidOrder);
    void sendDePaidOrderMessage(Order paidOrder);
    void sendShipOrderMessage(Order shippedOrder);
    void sendDeShippedOrderMessage(Order shippedOrder);
}
