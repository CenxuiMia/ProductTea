package com.cenxui.shop.aws.dynamodb.exceptions.server.product;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class ProductAmountSubtractException extends RepositoryServerException {
    public ProductAmountSubtractException() {
        super("subtract amount cannot null or less than 1");
    }
}
