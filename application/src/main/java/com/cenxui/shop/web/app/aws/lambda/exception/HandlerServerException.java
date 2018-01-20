package com.cenxui.shop.web.app.aws.lambda.exception;

public class HandlerServerException extends RuntimeException {
    public HandlerServerException(String s) {
        super(s);
    }
}
