package com.cenxui.shop.aws.dynamodb.exceptions.server.util;

import com.cenxui.shop.aws.dynamodb.exceptions.server.RepositoryServerException;

public class ItemUtilNotSupportClassTypeException extends RepositoryServerException {
    public ItemUtilNotSupportClassTypeException(String fieldName, String classType) {
        super(fieldName + " with class type " + classType + " not support");
    }
}
