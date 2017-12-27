package com.cenxui.shop.aws.dynamodb.exceptions.client.map.user;

import com.cenxui.shop.aws.dynamodb.exceptions.client.map.JsonMapException;

public class UserJsonMapException extends JsonMapException {
    public UserJsonMapException(String userJson) {
        super("User json value maps to user object fail, User :" + userJson);
    }
}
