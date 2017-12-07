package com.cenxui.tea.app.repositories.order;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class OrderKey extends Key {
    @NonNull String mail;
    @NonNull String orderDateTime;
}
