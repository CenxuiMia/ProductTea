package com.cenxui.tea.app.repositories.order;

import lombok.Value;

@Value(staticConstructor = "of")
public class OrderPaidLastKey extends Key {
    String paidDate;
    String paidTime;
    String mail;
    String orderDateTime;
}
