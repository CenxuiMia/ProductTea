package com.cenxui.tea.app.aws.dynamodb.exceptions.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;

public class OrderCannotDeShipException extends RepositoryException {
    public OrderCannotDeShipException(String mail, String time) {
        super("Order cannot ship. mail :" + mail + " time :" + time);
    }
}
