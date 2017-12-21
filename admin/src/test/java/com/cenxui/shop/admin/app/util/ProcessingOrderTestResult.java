package com.cenxui.shop.admin.app.util;

import com.cenxui.shop.repositories.order.Order;
import com.cenxui.shop.repositories.order.OrderProcessingLastKey;
import lombok.Value;

import java.util.List;

@Value
public class ProcessingOrderTestResult {
    List<Order> orders;
    OrderProcessingLastKey lastKey;

}
