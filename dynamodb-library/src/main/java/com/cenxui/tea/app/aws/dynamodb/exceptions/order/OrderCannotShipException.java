package com.cenxui.tea.app.aws.dynamodb.exceptions.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;

public class OrderCannotShipException extends RepositoryException {
    public OrderCannotShipException(String mail, String time, String shippedTime) {
        super("Order cannot ship. mail :" + mail + " time :" + time + " shippedTime :" + shippedTime);
    }
}