package com.cenxui.tea.app.repositories.product;

import lombok.Value;

@Value(staticConstructor = "of")
public class ProductKey {
    String name;
    String version;
}
