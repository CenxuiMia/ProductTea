package com.cenxui.tea.app.aws.dynamodb.exceptions.map.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;

public class OrderAlreadyExistException extends RepositoryException {
    public OrderAlreadyExistException(String e) {
        super(e);
    }
}
