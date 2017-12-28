package com.cenxui.shop.web.app.lambda.handlers;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.model.*;
import com.amazonaws.serverless.proxy.spark.SparkLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cenxui.shop.web.app.Application;
import com.cenxui.shop.web.app.lambda.log.AWSLambdaLogger;
import com.cenxui.shop.web.app.controller.util.Header;

import java.util.Map;

public class AuthLambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {
    private SparkLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    private AwsProxyResponse clientErrorResponse = new AwsProxyResponse();
    private AwsProxyResponse serverErrorResponse = new AwsProxyResponse();

    private boolean initialized = false;

    {
        clientErrorResponse.setStatusCode(400);
        serverErrorResponse.setStatusCode(500);
        try {
            handler = SparkLambdaContainerHandler.getAwsProxyHandler();
        } catch (ContainerInitializationException e) {
            e.printStackTrace();
        }
    }

    public final AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {
        if (!initialized) {
            defineRoutes();
            initialized = true;
        }

        Map<String, String> headers = awsProxyRequest.getHeaders();

        if (headers.containsKey(Header.MAIL)) return clientErrorResponse;

        String mail = awsProxyRequest.getRequestContext().getAuthorizer().getClaims().getEmail();

        if (mail == null || mail.isEmpty()) return clientErrorResponse;

        AwsProxyResponse response = null;

        try {
            headers.put(Header.MAIL, mail);

            response = handler.proxy(awsProxyRequest, context);

        }catch (Throwable e) {
            AWSLambdaLogger.log(context, e);
            return serverErrorResponse;
        }

        return response;
    }

    private void defineRoutes() {
        Application.defineAuthResources();
    };

}
