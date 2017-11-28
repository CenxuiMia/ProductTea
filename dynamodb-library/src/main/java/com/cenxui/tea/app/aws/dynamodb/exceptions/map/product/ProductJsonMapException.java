package com.cenxui.tea.app.aws.dynamodb.exceptions.map.product;

import com.cenxui.tea.app.aws.dynamodb.exceptions.map.JsonMapException;

public class ProductJsonMapException extends JsonMapException {
    public ProductJsonMapException(String productJson) {
        super("Product json value maps to product object fail, Product : " + productJson);
    }
}
