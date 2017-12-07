package com.cenxui.tea.app.repositories.order;

import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderPaidLastKey;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class CashReport {
    List<Order> orders;
    Double revenue;
}
