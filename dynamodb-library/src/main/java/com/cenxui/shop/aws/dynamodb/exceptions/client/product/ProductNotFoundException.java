package com.cenxui.shop.aws.dynamodb.exceptions.client.product;

import com.cenxui.shop.aws.dynamodb.exceptions.client.RepositoryClientException;

public class ProductNotFoundException extends RepositoryClientException {
    public ProductNotFoundException(String name, String version) {
        super("product name :" + name + "version :" + version + " not found.");
    }
}
