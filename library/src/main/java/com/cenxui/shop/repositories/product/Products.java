package com.cenxui.shop.repositories.product;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class Products {
   List<Product> products;
   ProductKey key;
}
