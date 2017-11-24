package com.cenxui.tea.app.repositories.product;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class ProductResult {
   List<Product> products;
   ProductKey key;
}
