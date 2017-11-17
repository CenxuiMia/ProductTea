package com.cenxui.tea.app.aws.dynamodb.exceptions.map;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepoistoryException;

public class ProductJsonMapException extends JsonMapException {
    public ProductJsonMapException(String e, String productJson) {
        super("Product Json value maps to Product object fail, Product : " + productJson + ", error" + e);
    }
}
