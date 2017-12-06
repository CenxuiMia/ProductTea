package com.cenxui.tea.app.aws.dynamodb.exceptions.product;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;

public class ProductsFormatException extends RepositoryException {
    public ProductsFormatException(String s) {
        super("current format is " + s + "but should be productName;version;count ");
    }
}
