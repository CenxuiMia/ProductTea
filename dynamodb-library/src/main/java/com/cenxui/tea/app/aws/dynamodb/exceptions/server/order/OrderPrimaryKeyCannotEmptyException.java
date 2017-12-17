package com.cenxui.tea.app.aws.dynamodb.exceptions.server.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.server.RepositoryServerException;

public class OrderPrimaryKeyCannotEmptyException extends RepositoryServerException {
    public OrderPrimaryKeyCannotEmptyException() {
        super("order primary key cannot be empty");
    }
}
