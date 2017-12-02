package com.cenxui.tea.app.aws.dynamodb.util;

class ItemUtilCannotAccessFieldException extends RuntimeException {
    ItemUtilCannotAccessFieldException(String fieldName) {
        super("cannot access field.");
    }
}
