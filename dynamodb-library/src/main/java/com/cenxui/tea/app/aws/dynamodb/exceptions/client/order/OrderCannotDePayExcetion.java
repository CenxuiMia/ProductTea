package com.cenxui.tea.app.aws.dynamodb.exceptions.client.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.client.RepositoryClientException;

public class OrderCannotDePayExcetion extends RepositoryClientException {
    public OrderCannotDePayExcetion(String mail, String time) {
        super("Order cannot depay. mail :" + mail + " time :" + time);
    }
}
