package com.cenxui.tea.app.aws.lambda.handlers.admin;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * This handler is used for admin to login
 */
public class AdminGetHandler implements RequestHandler<Object, Object> {
    @Override
    public Object handleRequest(Object input, Context context) {
        //TODO
        return "AdminGetHandler";
    }
}
