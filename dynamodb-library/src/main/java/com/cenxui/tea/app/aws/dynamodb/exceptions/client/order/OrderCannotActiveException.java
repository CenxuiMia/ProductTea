package com.cenxui.tea.app.aws.dynamodb.exceptions.client.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.client.RepositoryClientException;

public class OrderCannotActiveException extends RepositoryClientException {
    public OrderCannotActiveException(String mail, String time) {
        super("Order cannot active. mail :" + mail + " time :" + time );
    }
}
