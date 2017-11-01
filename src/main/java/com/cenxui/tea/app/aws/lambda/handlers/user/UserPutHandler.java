package com.cenxui.tea.app.aws.lambda.handlers.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UserPutHandler implements RequestHandler<Object, Object> {
    @Override
    public Object handleRequest(Object input, Context context) {
        //TODO
        return "UserPutHandler";
    }
}
