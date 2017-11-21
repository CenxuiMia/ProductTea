package com.cenxui.tea.app.aws.lambda.handers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.model.S3Event;

public class UpdateProductHandler implements RequestHandler<S3Event, String> {
    @Override
    public String handleRequest(S3Event input, Context context) {


        return null;
    }
}
