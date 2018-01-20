package com.cenxui.shop.web.app.aws.lambda.exception;

public class HandlerClientException extends RuntimeException {
    public HandlerClientException(String e) {
        super(e);
    }
}
