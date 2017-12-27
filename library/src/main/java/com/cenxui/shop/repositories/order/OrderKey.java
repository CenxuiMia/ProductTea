package com.cenxui.shop.repositories.order;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class OrderKey extends Key {
    @NonNull String mail;
    @NonNull String orderDateTime;
}
