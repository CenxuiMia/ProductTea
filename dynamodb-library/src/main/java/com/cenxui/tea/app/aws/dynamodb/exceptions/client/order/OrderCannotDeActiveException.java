package com.cenxui.tea.app.aws.dynamodb.exceptions.client.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.client.RepositoryClientException;

public class OrderCannotDeActiveException extends RepositoryClientException {
    public OrderCannotDeActiveException(String mail, String time) {
        super("Order cannot deActive. mail :" + mail + " time :" + time );
    }
}