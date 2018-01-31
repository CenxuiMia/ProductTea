package com.cenxui.shop.aws.dynamodb.exceptions.server.order;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class OrderCommentNotAllowedException extends RepositoryServerException {
    public OrderCommentNotAllowedException(String comment) {
        super("Order comment field is not allowed " + comment);
    }
}
