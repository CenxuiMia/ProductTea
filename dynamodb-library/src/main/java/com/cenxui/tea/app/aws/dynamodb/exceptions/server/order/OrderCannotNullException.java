package com.cenxui.tea.app.aws.dynamodb.exceptions.server.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.server.RepositoryServerException;

public class OrderCannotNullException extends RepositoryServerException {
    public OrderCannotNullException() {
        super("order cannot be null");
    }
}
