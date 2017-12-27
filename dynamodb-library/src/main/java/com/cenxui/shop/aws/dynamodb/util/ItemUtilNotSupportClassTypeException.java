package com.cenxui.shop.aws.dynamodb.util;

class ItemUtilNotSupportClassTypeException extends RuntimeException {
    ItemUtilNotSupportClassTypeException(String fieldName, String classType) {
        super(fieldName + " with class type " + classType + " not support");
    }
}
