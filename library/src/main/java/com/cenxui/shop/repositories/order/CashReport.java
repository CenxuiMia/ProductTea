package com.cenxui.shop.repositories.order;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class CashReport {
    List<Order> orders;
    Double revenue;
}
