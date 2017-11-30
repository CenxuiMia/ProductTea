package com.cenxui.tea.app.aws.dynamodb.exceptions.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;

public class OrderCannotDePayExcetion extends RepositoryException {
    public OrderCannotDePayExcetion(String mail, String time) {
        super("Order cannot depay. mail :" + mail + " time :" + time);
    }
}
