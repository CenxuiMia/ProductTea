package com.cenxui.shop.aws.dynamodb.exceptions.server.product;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class ProductPrimaryKeyCannotEmptyException extends RepositoryServerException {
    public ProductPrimaryKeyCannotEmptyException() {
        super("primary key cannot be empty");
    }
}
