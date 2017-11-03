package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.cenxui.tea.app.config.DynamoDBConfig;

class DynamoDBManager {
    private static final AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withRegion(DynamoDBConfig.REGION)
            .build();

    private static final DynamoDB dynaomDB = new DynamoDB(amazonDynamoDB);

    public static AmazonDynamoDB getAmazonDynamoDBClient() {
        return amazonDynamoDB;
    }

    public static DynamoDB getDynamoDB() {
        return dynaomDB;
    }
}
