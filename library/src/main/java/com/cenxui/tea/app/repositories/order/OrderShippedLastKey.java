package com.cenxui.tea.app.repositories.order;

import lombok.Value;

@Value(staticConstructor = "of")
public class OrderShippedLastKey extends Key {
    String shippedDate;
    String shippedTime;
    String mail;
    String orderDateTime;
}
