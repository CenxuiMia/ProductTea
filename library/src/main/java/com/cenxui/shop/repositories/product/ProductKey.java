package com.cenxui.shop.repositories.product;

import lombok.Value;

@Value(staticConstructor = "of")
public class ProductKey {
    String name;
    String version;
}
