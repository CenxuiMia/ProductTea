package com.cenxui.shop.web.app.service;

import com.cenxui.shop.repositories.order.Order;

public interface SendMessageService {

    void sendMessage(Order order);

}
