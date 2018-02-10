package com.cenxui.shop.aws.dynamodb.exceptions.server.util;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class ItemUtilCannotAccessFieldException extends RepositoryServerException {
    public ItemUtilCannotAccessFieldException(String fieldName) {
        super("cannot access field." + fieldName);
    }
}
