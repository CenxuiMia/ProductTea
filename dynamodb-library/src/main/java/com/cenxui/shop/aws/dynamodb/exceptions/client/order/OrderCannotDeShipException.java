package com.cenxui.shop.aws.dynamodb.exceptions.client.order;

import com.cenxui.shop.aws.dynamodb.exceptions.client.RepositoryClientException;

public class OrderCannotDeShipException extends RepositoryClientException {
    public OrderCannotDeShipException(String mail, String time) {
        super("Order cannot ship. mail :" + mail + " time :" + time);
    }
}
