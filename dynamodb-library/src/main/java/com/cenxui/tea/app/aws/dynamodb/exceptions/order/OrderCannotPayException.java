package com.cenxui.tea.app.aws.dynamodb.exceptions.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;

public class OrderCannotPayException extends RepositoryException {
    public OrderCannotPayException(String mail, String time, String paidDate, String paidTime) {
        super("Order cannot pay. mail :" + mail + " time :" + time +
                " paidDate :" + paidDate + " paidTime :" + paidTime);
    }
}