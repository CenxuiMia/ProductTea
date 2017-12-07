package com.cenxui.tea.app.aws.dynamodb.exceptions.client.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.client.RepositoryClientException;

public class OrderNotFoundException extends RepositoryClientException {
    public OrderNotFoundException(String mail, String time) {
        super("order mail :" + mail + " time :" + time + "not found.");
    }
}
