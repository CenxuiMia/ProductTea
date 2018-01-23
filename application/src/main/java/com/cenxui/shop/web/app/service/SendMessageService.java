package com.cenxui.shop.web.app.service;

import com.cenxui.shop.repositories.order.Order;

/**
 * send order message to end user. eg mail or sms
 */

public interface SendMessageService {

    void sendOrderMessage(Order order);

}
