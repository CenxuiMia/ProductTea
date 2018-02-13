package com.cenxui.shop.aws.dynamodb.exceptions.client.point;

import com.cenxui.shop.aws.dynamodb.exceptions.client.RepositoryClientException;

public class PointCannotUserException extends RepositoryClientException {
    public PointCannotUserException(Long value) {
        super("cannot use more point than you have");
    }
}
