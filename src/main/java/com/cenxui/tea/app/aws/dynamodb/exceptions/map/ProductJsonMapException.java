package com.cenxui.tea.app.aws.dynamodb.exceptions.map;

public class ProductJsonMapException extends JsonMapException {
    public ProductJsonMapException(String productJson) {
        super("Product Json value maps to Product object fail, Product : " + productJson);
    }
}
