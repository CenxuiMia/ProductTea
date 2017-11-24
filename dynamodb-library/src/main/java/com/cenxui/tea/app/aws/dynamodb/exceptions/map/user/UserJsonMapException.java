package com.cenxui.tea.app.aws.dynamodb.exceptions.map.user;

import com.cenxui.tea.app.aws.dynamodb.exceptions.map.JsonMapException;

public class UserJsonMapException extends JsonMapException {
    public UserJsonMapException(String userJson) {
        super("User json value maps to user object fail, User :" + userJson);
    }
}
