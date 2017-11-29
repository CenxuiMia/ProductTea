package com.cenxui.tea.app.repositories.order;

import lombok.Value;

@Value(staticConstructor = "of")
public class ShippedOrderKey extends Key {
    String shippedTime;
}
