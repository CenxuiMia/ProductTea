package com.cenxui.tea.app.aws.dynamodb.exceptions.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;

public class OrderCannotActiveException extends RepositoryException {
    public OrderCannotActiveException(String mail, String time) {
        super("Order cannot active. mail :" + mail + " time :" + time );
    }
}
