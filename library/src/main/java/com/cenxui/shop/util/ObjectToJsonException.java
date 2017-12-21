package com.cenxui.shop.util;

class ObjectToJsonException extends RuntimeException {
    ObjectToJsonException(Object object) {
        super(object.toString() + " object to json error, this should not happened.");
    }
}
