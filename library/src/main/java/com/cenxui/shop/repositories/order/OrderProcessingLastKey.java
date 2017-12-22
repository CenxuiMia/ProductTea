package com.cenxui.shop.repositories.order;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class OrderProcessingLastKey extends Key {
    @NonNull String processingDate;
    @NonNull String owner;
    @NonNull String mail;
    @NonNull String orderDateTime;
}
