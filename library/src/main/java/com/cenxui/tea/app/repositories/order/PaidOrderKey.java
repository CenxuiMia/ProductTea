package com.cenxui.tea.app.repositories.order;

import lombok.Value;

@Value(staticConstructor = "of")
public class PaidOrderKey extends Key {
    String paidDate;
    String paidTime;
}
