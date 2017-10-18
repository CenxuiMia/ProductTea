package com.cenxui.tea.app.repositories;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.*;
import com.cenxui.tea.app.repositories.catagory.Product;
import com.cenxui.tea.dynamodb.util.AttributeValueUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

@RunWith(JUnit4.class)
public class ProductRepositoryIntegrationTest {

    private DynamoDBProxyServer server;
    private AmazonDynamoDB dynamodb;
    private String tableName = "TeaProduct";

    @Before
    public void setUp() throws Exception {

        // Create an in-memory and in-process instance of DynamoDB Local that runs over HTTP
        final String[] localArgs = { "-inMemory" };

        server = ServerRunner.createServerFromCommandLineArgs(localArgs);
        server.start();

        dynamodb = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                // we can use any region here
                new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();

        // use the DynamoDB API over HTTP
        listTables(dynamodb.listTables(), "DynamoDB Local over HTTP");

        createTable();

    }

    private void createTable() {

        AttributeDefinition name = new AttributeDefinition(Product.NAME, "S");
        AttributeDefinition version = new AttributeDefinition(Product.VERSION, "N");

        Collection<AttributeDefinition> attributeDefinitions =
                Arrays.asList(name, version);

        KeySchemaElement primaryKey = new KeySchemaElement(Product.NAME, KeyType.HASH);
        KeySchemaElement sortKey = new KeySchemaElement(Product.VERSION, KeyType.RANGE);



        Collection<KeySchemaElement> keySchemaElements =
                Arrays.asList(primaryKey, sortKey);

        ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput(1l,1l);

        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName(tableName)
                .withAttributeDefinitions(attributeDefinitions)
                .withKeySchema(keySchemaElements)
                .withProvisionedThroughput(provisionedThroughput);


        CreateTableResult createTableResult = dynamodb.createTable(createTableRequest);
        System.out.println(createTableResult);
    }

    @Test
    public void putItems() {

        List<String> images = Arrays.asList("a", "b", "c");

        List<Product> products = Collections.unmodifiableList(Arrays.asList(
                Product.of(
                        "black tea" ,
                        1,
                        "good tea from mia banana",
                        "sm",
                        "bm",
                        images, Boolean.TRUE, 100.0, "mia"),
                Product.of(
                        "green tea" ,
                        1,
                        "standard tea from cenxui banana",
                        "sm",
                        "bm",
                        images, Boolean.TRUE, 100.0, "cenxui"),
                Product.of(
                        "woolong tea",
                        1,
                        "woolong tea from cenxui mia",
                        "sm",
                        "bm",
                        images, Boolean.TRUE, 200.0, "cenxui"),
                Product.of(
                        "mountain green tea" ,
                        1,
                        "mountain tea from cenxui mia",
                        "sm",
                        "bm",
                        images, Boolean.TRUE, 200.0, "mia")
        ));

        for (Product product : products) {
            dynamodb.putItem(tableName, AttributeValueUtil.getProductAttributeMap(product));
        }


        ScanRequest scanRequest = new ScanRequest().withTableName(tableName);

        ScanResult scanResult = dynamodb.scan(scanRequest);

        for (Map item : scanResult.getItems()) {
            System.out.println(item);
        }


    }


    private void listTables(ListTablesResult result, String method) {
        System.out.println("found " + Integer.toString(result.getTableNames().size()) + " tables with " + method);
        for(String table : result.getTableNames()) {
            System.out.println(table);
        }
    }


    @After
    public void shutDown() throws Exception {
        // Stop the DynamoDB Local endpoint
        if(server != null) {
            server.stop();
        }
    }
}
