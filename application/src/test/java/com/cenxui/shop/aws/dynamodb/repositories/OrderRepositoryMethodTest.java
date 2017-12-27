package com.cenxui.shop.aws.dynamodb.repositories;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.cenxui.shop.web.app.config.DynamoDBConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class OrderRepositoryMethodTest {

    private Table table;
    private final String tableName = DynamoDBConfig.ORDER_TABLE;

    @Before
    public void setUp() {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
                .standard()
                .withRegion(Regions.AP_NORTHEAST_1).build(); // in product state do not input any credential
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        table = dynamoDB.getTable(tableName);
    }


    @Test
    public void isActive() {


    }
}
