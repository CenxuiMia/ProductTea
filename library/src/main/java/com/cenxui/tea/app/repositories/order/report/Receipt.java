package com.cenxui.tea.app.repositories.order.report;

import lombok.Value;

@Value(staticConstructor = "of")
public class Receipt {
    String mail;
    String orderDateTime;
    String paymentMethod;
    Float price;
}
