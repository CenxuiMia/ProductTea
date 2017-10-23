package com.cenxui.tea.app.integration.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.*;
import com.cenxui.tea.app.integration.repositories.catagory.Product;
import com.cenxui.tea.app.integration.repositories.util.DynamoDBLocalUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collection;

@RunWith(JUnit4.class)
public class OrderRepositoryIntegrationTest {

    static {
        System.setProperty("java.library.path", "sqlite4java.jar");
    }

    private DynamoDBProxyServer server;
    private AmazonDynamoDB amazonDynamoDB;
    private Table table;
    private String tableName = "TeaOrder";

    @Before
    public void setUp() {
        server = DynamoDBLocalUtil.runDynamoDBInMemory();
        amazonDynamoDB = DynamoDBLocalUtil.getDynamoDBClient();

    }

    @Test
    public void dataLifeCycle() {


    }

    private void createTable() {
        //TODO

//        AttributeDefinition name = new AttributeDefinition(Product.NAME, "S");
//        AttributeDefinition version = new AttributeDefinition(Product.VERSION, "N");
//
//        Collection<AttributeDefinition> attributeDefinitions =
//                Arrays.asList(name, version);
//
//        KeySchemaElement primaryKey = new KeySchemaElement(Product.NAME, KeyType.HASH);
//        KeySchemaElement sortKey = new KeySchemaElement(Product.VERSION, KeyType.RANGE);
//
//
//        Collection<KeySchemaElement> keySchemaElements =
//                Arrays.asList(primaryKey, sortKey);
//
//        ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput(1l, 1l);
//
//        CreateTableRequest createTableRequest = new CreateTableRequest()
//                .withTableName(tableName)
//                .withAttributeDefinitions(attributeDefinitions)
//                .withKeySchema(keySchemaElements)
//                .withProvisionedThroughput(provisionedThroughput);
//
//        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
//
//        table = dynamoDB.createTable(createTableRequest);
    }

    @After
    public void shutDown() throws Exception {
        if (server != null) {
            server.stop();
        }
    }

}
