package com.cenxui.tea.app.repositories.product;

import lombok.Value;

@Value(staticConstructor = "of")
public class Price {
    String currency;
    Float money;
}
