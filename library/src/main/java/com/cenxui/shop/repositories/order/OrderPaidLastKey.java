package com.cenxui.shop.repositories.order;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class OrderPaidLastKey extends Key {
    @NonNull String paidDate;
    @NonNull String paidTime;
    @NonNull String mail;
    @NonNull String orderDateTime;
}
