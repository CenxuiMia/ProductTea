package com.cenxui.tea.app.aws.dynamodb.util;

public class ItemUtilException extends RuntimeException {
    public ItemUtilException() {
        super("cannot access field.");
    }
}
