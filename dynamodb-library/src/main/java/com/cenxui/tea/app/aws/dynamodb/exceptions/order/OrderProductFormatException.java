package com.cenxui.tea.app.aws.dynamodb.exceptions.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;

public class OrderProductFormatException extends RepositoryException {
    public OrderProductFormatException(String s) {
        super("current format is " + s + "but should be String;String;Float");
    }
}
