package com.cenxui.tea.app.repositories.order;

import lombok.Value;

@Value(staticConstructor = "of")
public class OrderProcessingLastKey extends Key {
    String processingDate;
    String owner;
    String mail;
    String orderDateTime;
}
