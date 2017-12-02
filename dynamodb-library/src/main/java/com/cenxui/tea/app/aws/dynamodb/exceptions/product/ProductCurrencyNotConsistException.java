package com.cenxui.tea.app.aws.dynamodb.exceptions.product;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;

public class ProductCurrencyNotConsistException extends RepositoryException {
    public ProductCurrencyNotConsistException(String e) {
        super("product currency not consist" + e); //todo
    }
}
