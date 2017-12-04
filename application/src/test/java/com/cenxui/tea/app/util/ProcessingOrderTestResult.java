package com.cenxui.tea.app.util;

import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderProcessingLastKey;
import lombok.Value;

import java.util.List;

@Value
public class ProcessingOrderTestResult {
    List<Order> orders;
    OrderProcessingLastKey lastKey;

}
