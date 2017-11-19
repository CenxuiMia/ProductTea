package com.cenxui.tea.app.util;

public class ObjectToJsonException extends RuntimeException {
    public ObjectToJsonException(Object object) {
        super(object.toString() + " object to json error, this should not happened.");
    }
}
