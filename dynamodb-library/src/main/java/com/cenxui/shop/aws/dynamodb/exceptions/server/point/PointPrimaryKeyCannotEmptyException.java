package com.cenxui.shop.aws.dynamodb.exceptions.server.point;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class PointPrimaryKeyCannotEmptyException extends RepositoryServerException {

    public PointPrimaryKeyCannotEmptyException() {
        super("Point primary key cannot be empty");
    }
}
