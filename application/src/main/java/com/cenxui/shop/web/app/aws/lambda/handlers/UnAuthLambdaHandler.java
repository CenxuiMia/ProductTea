package com.cenxui.shop.web.app.aws.lambda.handlers;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spark.SparkLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cenxui.shop.web.app.Application;
import com.cenxui.shop.web.app.aws.lambda.exception.HandlerServerException;
import com.cenxui.shop.web.app.aws.lambda.log.AWSLambdaLogger;

public class UnAuthLambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {
    private SparkLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    private boolean initialized = false;

    public final AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {
        initialContainer();

        AwsProxyResponse response = null;

        try {
            response = handler.proxy(awsProxyRequest, context);
        }catch (Exception e) {
            AWSLambdaLogger.log(context, e);
        }

        return response;
    }

    private void initialContainer() {
        if (!initialized) {
            initialized = true;
            try {
                handler = SparkLambdaContainerHandler.getAwsProxyHandler();
                defineRoutes();
            } catch (ContainerInitializationException e) {
                throw new HandlerServerException("Unath Spark container initial error: " + e.getMessage());
            }
        }
    }

    private void defineRoutes() {
        Application.defineUnAuthResources();
    }

}
