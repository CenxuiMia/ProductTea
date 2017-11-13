package com.cenxui.tea.app.aws.lambda.handlers;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.jaxrs.AwsProxySecurityContext;
import com.amazonaws.serverless.proxy.internal.model.*;
import com.amazonaws.serverless.proxy.spark.SparkLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cenxui.tea.app.Application;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class AuthLambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {
    private SparkLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    public static final String MAIL = "censmail";

    private boolean initialized = false;

    {
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
        try {

           headers.put(MAIL, awsProxyRequest.getRequestContext().getAuthorizer().getClaims().getEmail());

        }catch (Exception e) {
            e.printStackTrace();
            headers.put(MAIL, "ERROR");
        }



        return handler.proxy(awsProxyRequest, context);
    }

    private void defineRoutes() {
        Application.defineAuthResources();
    };

}
