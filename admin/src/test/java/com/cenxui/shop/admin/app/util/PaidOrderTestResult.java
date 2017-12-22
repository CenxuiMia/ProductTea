package com.cenxui.shop.admin.app.util;

import com.cenxui.shop.repositories.order.Order;
import com.cenxui.shop.repositories.order.OrderPaidLastKey;
import lombok.Value;

import java.util.List;

@Value
public class PaidOrderTestResult {

    List<Order> orders;
    OrderPaidLastKey lastKey;
}
