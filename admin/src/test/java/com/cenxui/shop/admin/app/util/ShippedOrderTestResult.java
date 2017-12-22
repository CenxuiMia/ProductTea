package com.cenxui.shop.admin.app.util;

import com.cenxui.shop.repositories.order.Order;
import com.cenxui.shop.repositories.order.OrderShippedLastKey;
import lombok.Value;

import java.util.List;

@Value
public class ShippedOrderTestResult {
    List<Order> orders;
    OrderShippedLastKey lastKey;
}
