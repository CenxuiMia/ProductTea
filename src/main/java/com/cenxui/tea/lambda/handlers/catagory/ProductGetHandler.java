package com.cenxui.tea.lambda.handlers.catagory;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cenxui.tea.app.services.catagory.ProductController;


/**
 * This handler is used to query the product list
 */
public class ProductGetHandler implements RequestHandler<Object, Object> {
    @Override
    public Object handleRequest(Object input, Context context) {

        return null;
    }
}