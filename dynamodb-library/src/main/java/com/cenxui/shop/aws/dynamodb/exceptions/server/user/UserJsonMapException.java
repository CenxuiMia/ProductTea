package com.cenxui.shop.aws.dynamodb.exceptions.server.user;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class UserJsonMapException extends RepositoryServerException {
    public UserJsonMapException(String userJson) {
        super("User table item maps to user object fail, User :" + userJson);
    }
}
