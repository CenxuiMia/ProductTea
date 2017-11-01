package com.cenxui.tea.app.integration.repositories.util;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;

public class DynamoDBLocalUtil {
    private static DynamoDBProxyServer server;
    public static DynamoDBProxyServer getDynamoDBProxyServerInMemory() {
        // Create an in-memory and in-process instance of DynamoDB Local that runs over HTTP
        final String[] localArgs = {
                "-inMemory",
                "-port",
                "8000"};

        if (server == null) {
            try {
                server = ServerRunner.createServerFromCommandLineArgs(localArgs);
                server.start();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("cannot set up server");
            }
        }

        return server;
    }

    public static AmazonDynamoDB getAmazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                // we can use any region here
                new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();
        return amazonDynamoDB;
    }

}
