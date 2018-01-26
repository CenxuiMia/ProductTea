package com.cenxui.shop.aws.dynamodb.exceptions.client.user;

import com.cenxui.shop.aws.dynamodb.exceptions.client.RepositoryClientException;

public class UserBirthdayExistException extends RepositoryClientException {
    public UserBirthdayExistException() {
        super("User birthday cannot be updated.");
    }
}
