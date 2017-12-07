package com.cenxui.tea.app.aws.dynamodb.exceptions.client.product;

import com.cenxui.tea.app.aws.dynamodb.exceptions.client.RepositoryClientException;

public class ProductsFormatException extends RepositoryClientException {
    public ProductsFormatException(String s) {
        super("current format is " + s + "but should be productName;version;count ");
    }
}
