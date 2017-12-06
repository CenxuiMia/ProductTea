package com.cenxui.tea.admin.app.util;

import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderKey;
import lombok.Value;

import java.util.List;

@Value
public class OrderTestResult {
    List<Order> orders;
    OrderKey lastKey;
}
