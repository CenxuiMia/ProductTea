package com.cenxui.shop.repositories.order;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class OrderBankLastKey extends Key {
    @NonNull String bankInformation;
    @NonNull String mail;
    @NonNull String orderDateTime;
}
