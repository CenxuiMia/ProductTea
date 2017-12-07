package com.cenxui.tea.app.aws.dynamodb.exceptions.client.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.client.RepositoryClientException;

public class OrderCannotPayException extends RepositoryClientException {
    public OrderCannotPayException(String mail, String time, String paidDate, String paidTime) {
        super("Order cannot pay. mail :" + mail + " time :" + time +
                " paidDate :" + paidDate + " paidTime :" + paidTime);
    }
}