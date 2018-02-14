package com.cenxui.shop.aws.dynamodb.exceptions.server.point;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class PointJsonMapException extends RepositoryServerException {
    public PointJsonMapException(String pointJson) {
        super("Point table item map to point fail, pointJson :" + pointJson);
    }
}
