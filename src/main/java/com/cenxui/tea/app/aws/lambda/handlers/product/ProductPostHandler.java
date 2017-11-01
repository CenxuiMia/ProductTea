package com.cenxui.tea.app.aws.lambda.handlers.product;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * This handler is used for a customer to buy a product
 */
public class ProductPostHandler implements RequestHandler<Object, Object> {
    @Override
    public Object handleRequest(Object input, Context context) {
        //TODO
        return "ProductPostHandler";
    }
}
