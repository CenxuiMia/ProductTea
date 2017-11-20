package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

class DynamoDBManager {

    static AmazonDynamoDB getAmazonDynamoDBClient(String region) {

        return AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
    }

    static AmazonDynamoDB getAmazonDynamoDBClientLocal(String url, String region) {
       return AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration(url, region)).build();
    }

    static DynamoDB getDynamoDB(String region) {
        return new DynamoDB(getAmazonDynamoDBClient(region));
    }

    static DynamoDB getDynamoDBLocal(String url, String region) {
        return new DynamoDB(getAmazonDynamoDBClientLocal(url, region));
    }


}
