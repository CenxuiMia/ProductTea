package com.cenxui.tea.app.aws.dynamodb.exceptions.map;

import com.cenxui.tea.app.repositories.product.Product;

public class ProductMapJsonException extends MapJsonException {
    public ProductMapJsonException(Object product) {
        super("Product list maps to json fail," + product);
    }
}
