package com.cenxui.tea.app.repositories.order.report;

import com.cenxui.tea.app.repositories.order.OrderKey;
import lombok.Value;

@Value(staticConstructor = "of")
public class Receipt {
    OrderKey key;
    String paymentMethod;
    Float price;
}
