package com.cenxui.tea.app.aws.dynamodb.exceptions.map.user;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;

public class UserNotFoundException extends RepositoryException {
    public UserNotFoundException(String e) {
        super(e); //todo
    }
}
