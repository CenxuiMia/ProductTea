package com.cenxui.tea.app.aws.dynamodb.exceptions.client.user;

import com.cenxui.tea.app.repositories.user.User;

import java.util.List;

public class UserProfileException extends RuntimeException {
    public UserProfileException(List<User> users) {
        super("Profile count cannot be multiple, now is " + users);
    }
}
