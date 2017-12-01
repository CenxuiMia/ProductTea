package com.cenxui.tea.app.aws.dynamodb.exceptions.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;

public class OrderNotFoundException extends RepositoryException {
    public OrderNotFoundException(String mail, String time) {
        super("order mail :" + mail + " time :" + time + "not found.");
    }
}
