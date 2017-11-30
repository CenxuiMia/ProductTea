package com.cenxui.tea.app.aws.dynamodb.exceptions.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.util.JsonUtil;

public class OrderCannotActiveException extends RepositoryException {
    public OrderCannotActiveException(String mail, String time) {
        super("Order cannot active. mail :" + mail + " time :" + time );
    }
}
