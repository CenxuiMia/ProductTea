package com.cenxui.shop.aws.dynamodb.exceptions.server.order;

import com.cenxui.shop.aws.dynamodb.exceptions.client.RepositoryClientException;

public class OrderProductNotFoundException extends RepositoryClientException {
    public OrderProductNotFoundException(String productName , String version) {
        super(String.format("productName :%s version :%s not found",productName, version));
    }
}
