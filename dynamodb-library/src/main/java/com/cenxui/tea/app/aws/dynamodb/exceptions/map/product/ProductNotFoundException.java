package com.cenxui.tea.app.aws.dynamodb.exceptions.map.product;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;
import com.cenxui.tea.app.repositories.product.Product;

public class ProductNotFoundException extends RepositoryException {
    public ProductNotFoundException(String name) {
        super("product name :" + name + " not found.");
    }

    public ProductNotFoundException(String name, String version) {
        super("product name :" + name + "version :" + version + " not found.");
    }
}
