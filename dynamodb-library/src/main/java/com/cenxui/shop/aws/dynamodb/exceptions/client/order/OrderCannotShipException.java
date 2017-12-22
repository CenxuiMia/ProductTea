package com.cenxui.shop.aws.dynamodb.exceptions.client.order;

import com.cenxui.shop.aws.dynamodb.exceptions.client.RepositoryClientException;

public class OrderCannotShipException extends RepositoryClientException {
    public OrderCannotShipException(String mail, String time, String shippedTime) {
        super("Order cannot ship. mail :" + mail + " time :" + time + " shippedTime :" + shippedTime);
    }
}