package com.cenxui.tea.admin.app.util;

import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderPaidLastKey;
import lombok.Value;

import java.util.List;

@Value
public class PaidOrderTestResult {

    List<Order> orders;
    OrderPaidLastKey lastKey;
}
