package com.cenxui.shop.aws.dynamodb.util;

class ItemUtilCannotAccessFieldException extends RuntimeException {
    ItemUtilCannotAccessFieldException(String fieldName) {
        super("cannot access field.");
    }
}
