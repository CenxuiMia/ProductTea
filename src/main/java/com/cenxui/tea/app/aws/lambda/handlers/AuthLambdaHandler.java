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

        AwsProxyResponse response = null;

        try {
            headers.put(MAIL, awsProxyRequest.getRequestContext().getAuthorizer().getClaims().getEmail());

            response = handler.proxy(awsProxyRequest, context);

        }catch (Exception e) {
            context.getLogger().log(e.getMessage());
        }

        return response;
    }

    private void defineRoutes() {
        Application.defineAuthResources();
    };

}
