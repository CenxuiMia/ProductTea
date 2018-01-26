package com.cenxui.shop.web.app.aws.lambda.reponse;

import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;

import java.util.Map;
import java.util.TreeMap;

public class HandlerErrorResponse extends AwsProxyResponse{
    public HandlerErrorResponse() {
        Map<String, String> headers = new TreeMap<>();
        headers.put("Access-Control-Allow-Origin", "*");
    }

}
