package com.cenxui.tea.app.repositories.order;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class OrderResult {
    List<Order> orders;
    OrderKey key;
}
