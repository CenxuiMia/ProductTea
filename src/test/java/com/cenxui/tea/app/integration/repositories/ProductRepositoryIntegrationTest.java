package com.cenxui.tea.app.integration.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.*;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.integration.repositories.util.DynamoDBLocalUtil;
import com.cenxui.tea.aws.dynamodb.util.ItemUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

@RunWith(JUnit4.class)
public class ProductRepositoryIntegrationTest {

    private DynamoDBProxyServer server;
    private AmazonDynamoDB amazonDynamoDB;
    private Table table;
    private String tableName = "TeaProduct";

    @Before
    public void setUp() throws Exception {

        server = DynamoDBLocalUtil.getDynamoDBProxyServerInMemory();

        amazonDynamoDB = DynamoDBLocalUtil.getAmazonDynamoDB();

        // use the DynamoDB API over HTTP
        listTables(amazonDynamoDB.listTables(), "DynamoDB Local over HTTP");

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

        ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput(1l, 1l);

        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName(tableName)
                .withAttributeDefinitions(attributeDefinitions)
                .withKeySchema(keySchemaElements)
                .withProvisionedThroughput(provisionedThroughput);

        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

        table = dynamoDB.createTable(createTableRequest);
    }

    @Test
    public void testDataLifeCycle() {
        putItems();

        listAllItem();

        deleteItem();

        listAllItem();

        updateItem();

        listAllItem();
    }

    private void putItems() {

        List<String> images = Arrays.asList("a", "b", "c");

        List<Product> products = Collections.unmodifiableList(Arrays.asList(
                Product.of(
                        "black tea",
                        1,
                        "good tea from mia banana",
                        "sm",
                        "bm",
                        images, Boolean.TRUE, 100.0, "mia"),
                Product.of(
                        "green tea",
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
                        "mountain green tea",
                        1,
                        "mountain tea from cenxui mia",
                        "sm",
                        "bm",
                        images, Boolean.TRUE, 200.0, "mia")
        ));

        for (Product product : products) {

            table.putItem(ItemUtil.getProductItem(product));
        }
    }

    private void deleteItem() {
        table.deleteItem(Product.NAME, "black tea", Product.VERSION, 1);
        System.out.println("---------------delete item black tea version 1------------------");
    }

    private void listAllItem() {
        ScanRequest scanRequest = new ScanRequest().withTableName(tableName);

        ScanResult scanResult = amazonDynamoDB.scan(scanRequest);

        for (Map item : scanResult.getItems()) {
            System.out.println(item);
        }
    }

    public void updateItem() {
        Map<String, String> expressionAttributeNames = new HashMap<String, String>();
        expressionAttributeNames.put("#P", Product.PRICE);

        Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
        expressionAttributeValues.put(":val1",
                50); //Price

        UpdateItemOutcome outcome = table.updateItem(
                Product.NAME,          // key attribute name
                "green tea",           // key attribute value
                Product.VERSION,
                1,
                "set #P = #P - :val1", // UpdateExpression
                expressionAttributeNames,
                expressionAttributeValues);

        System.out.println("update outcome : " + outcome);
    }


    private void listTables(ListTablesResult result, String method) {
        System.out.println("found " + Integer.toString(result.getTableNames().size()) + " tables with " + method);
        for (String table : result.getTableNames()) {
            System.out.println(table);
        }
    }


    @After
    public void shutDown() throws Exception {
        // Stop the DynamoDB Local endpoint
        if (server != null) {
            server.stop();
        }
    }
}
