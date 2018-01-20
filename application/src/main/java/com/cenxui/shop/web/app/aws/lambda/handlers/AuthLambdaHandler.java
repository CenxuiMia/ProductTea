package com.cenxui.shop.web.app.aws.lambda.handlers;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.model.*;
import com.amazonaws.serverless.proxy.spark.SparkLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cenxui.shop.web.app.Application;
import com.cenxui.shop.web.app.aws.lambda.exception.HandlerClientException;
import com.cenxui.shop.web.app.aws.lambda.exception.HandlerServerException;
import com.cenxui.shop.web.app.aws.lambda.log.AWSLambdaLogger;
import com.cenxui.shop.web.app.aws.lambda.reponse.HandlerErrorResponse;
import com.cenxui.shop.web.app.controller.util.Header;

import java.util.Map;

public class AuthLambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {
    private SparkLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    private boolean initialized = false;

    public final AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {
        try {
            initialContainer();

            Map<String, String> headers = awsProxyRequest.getHeaders();

            if (headers.containsKey(Header.MAIL))
                throw new HandlerClientException("Handler request header cannot exist.");

            String mail = awsProxyRequest.getRequestContext().getAuthorizer().getClaims().getEmail();

            if (mail == null || mail.isEmpty())
                throw new HandlerServerException("Handler request mail attribute of authorizer claims not exist.");

            headers.put(Header.MAIL, mail);
        }catch (HandlerClientException e) {
            return getAwsProxyResponse(context, e, 400);
        }catch (HandlerServerException e) {
            return getAwsProxyResponse(context, e, 500);
        }
        return handler.proxy(awsProxyRequest, context);
    }

    private AwsProxyResponse getAwsProxyResponse(Context context, Exception e ,int statusCode) {
        AWSLambdaLogger.log(context, e);
        AwsProxyResponse response = new HandlerErrorResponse();
        response.setStatusCode(statusCode);
        response.setBody(e.getMessage());
        return response;
    }

    private void initialContainer() {
        if (!initialized) {
            try {
                initialized = true;
                handler = SparkLambdaContainerHandler.getAwsProxyHandler();
                defineRoutes();
            }catch (ContainerInitializationException e) {
                throw new HandlerServerException("Auth Spark container initial error: " + e.getMessage());
            }
        }
    }

    private void defineRoutes() {
        Application.defineAuthResources();
    };
}
