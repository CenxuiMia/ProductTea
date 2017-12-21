package com.cenxui.shop.aws.dynamodb.exceptions.client.map;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class JsonMapException extends RepositoryServerException {
    protected JsonMapException(String e) {
        super(e);
    }
}
