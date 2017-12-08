package com.cenxui.tea.app.aws.dynamodb.exceptions.server.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.server.RepositoryServerException;

public class OrderShippedWayException extends RepositoryServerException {
    public OrderShippedWayException(String e) {
        super("order shippedWay not allow shippedWay :" + e);
    }
}
