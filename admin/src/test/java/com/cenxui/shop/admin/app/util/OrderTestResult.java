package com.cenxui.shop.admin.app.util;

import com.cenxui.shop.repositories.order.Order;
import com.cenxui.shop.repositories.order.OrderKey;
import lombok.Value;

import java.util.List;

@Value
public class OrderTestResult {
    List<Order> orders;
    OrderKey lastKey;
}
