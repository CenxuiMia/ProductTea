package com.cenxui.tea.admin.app.util;

import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderShippedLastKey;
import lombok.Value;

import java.util.List;

@Value
public class ShippedOrderTestResult {
    List<Order> orders;
    OrderShippedLastKey lastKey;
}
