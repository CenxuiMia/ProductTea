package com.cenxui.tea.app.aws.lambda.handlers;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spark.SparkLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cenxui.tea.app.Application;

public class LambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {
    private SparkLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    private boolean initialized = false;

    {
        try {
            handler = SparkLambdaContainerHandler.getAwsProxyHandler();
        } catch (ContainerInitializationException e) {
            e.printStackTrace();
        }
    }

    public AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {
        if (!initialized) {
            defineRoutes();
            initialized = true;
        }

        return handler.proxy(awsProxyRequest, context);
    }

    private void defineRoutes() {
        Application.defineResources();
    }
}
