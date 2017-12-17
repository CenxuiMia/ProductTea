package com.cenxui.tea.app.aws.dynamodb.exceptions.server.user;

import com.cenxui.tea.app.aws.dynamodb.exceptions.server.RepositoryServerException;

public class UserPrimaryKeyCannotEmptyException extends RepositoryServerException {
    public UserPrimaryKeyCannotEmptyException() {
        super("user primary key cannot be empty");
    }
}
