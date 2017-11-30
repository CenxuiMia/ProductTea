package com.cenxui.tea.app.aws.dynamodb.exceptions.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;

public class OrderCannotDeActiveException extends RepositoryException {
    public OrderCannotDeActiveException(String mail, String time) {
        super("Order cannot deActive. mail :" + mail + " time :" + time );
    }
}