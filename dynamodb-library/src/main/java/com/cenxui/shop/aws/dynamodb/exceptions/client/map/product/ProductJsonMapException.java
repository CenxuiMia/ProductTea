package com.cenxui.shop.aws.dynamodb.exceptions.client.map.product;

import com.cenxui.shop.aws.dynamodb.exceptions.client.map.JsonMapException;

public class ProductJsonMapException extends JsonMapException {
    public ProductJsonMapException(String productJson) {
        super("Product json value maps to product object fail, Product : " + productJson);
    }
}
