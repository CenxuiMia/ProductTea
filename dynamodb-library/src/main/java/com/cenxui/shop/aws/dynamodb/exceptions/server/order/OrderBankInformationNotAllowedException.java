package com.cenxui.shop.aws.dynamodb.exceptions.server.order;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class OrderBankInformationNotAllowedException extends RepositoryServerException {
    public OrderBankInformationNotAllowedException(String paymentMethod, String bankInformation) {
        super("Order bankInformation is not allowed " + paymentMethod +", " + bankInformation);
    }
}
