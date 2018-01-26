package com.cenxui.shop.aws.dynamodb.exceptions.server.product;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class ProductJsonMapException extends RepositoryServerException {
    public ProductJsonMapException(String productJson) {
        super("Product table item maps to product object fail, Product : " + productJson);
    }
}
