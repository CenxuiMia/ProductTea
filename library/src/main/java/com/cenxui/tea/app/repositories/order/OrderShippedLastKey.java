package com.cenxui.tea.app.repositories.order;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class OrderShippedLastKey extends Key {
    @NonNull String shippedDate;
    @NonNull String shippedTime;
    @NonNull String mail;
    @NonNull String orderDateTime;
}
