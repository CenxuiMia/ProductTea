package com.cenxui.tea.app.aws.dynamodb.local.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.*;
import com.cenxui.tea.app.aws.dynamodb.local.repositories.util.DynamoDBLocalUtil;
import com.cenxui.tea.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.product.Product;
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
    private String tableName = DynamoDBConfig.PRODUCT_TABLE;

    @Before
    public void setUp() throws Exception {

        server = DynamoDBLocalUtil.getDynamoDBProxyServerInMemory();

        amazonDynamoDB = DynamoDBLocalUtil.getAmazonDynamoDB();

        // use the DynamoDB API over HTTP
        listTables(amazonDynamoDB.listTables(), "DynamoDB Local over HTTP");

        createTable();

    }

    private void createTable() {

        AttributeDefinition name = new AttributeDefinition(Product.PRODUCT_NAME, "S");
        AttributeDefinition version = new AttributeDefinition(Product.VERSION, "N");

        Collection<AttributeDefinition> attributeDefinitions =
                Arrays.asList(name, version);

        KeySchemaElement primaryKey = new KeySchemaElement(Product.PRODUCT_NAME, KeyType.HASH);
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
//
//        final List<Product> products = new ArrayList<>();
//
//        table.scan().forEach(
//                (s) -> {
//                   ObjectMapper objectMapper = new ObjectMapper();
//                    try {
//                        Product product = objectMapper.readValue(s.toJSON(), ItemProduct.class).getItem();
//                        products.add(product);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//        );
//
//        products.stream().forEach(System.out::println);
//
//

//        ScanRequest scanRequest = new ScanRequest()
//                .withTableName(tableName);
//
//        ScanResult result = amazonDynamoDB.scan(scanRequest);
//        for (Map<String, AttributeValue> item : result.getItems()){
//
//        }
//
//
//        System.out.println("====================print products========================");
//
//        products.stream().forEach(System.out::println);

//        listAllItem();
//
//        deleteItem();
//
//        listAllItem();
//
//        updateItem();
//
//        listAllItem();
    }

    private void putItems() {
//
//        List<String> images = Arrays.asList("a", "b", "c");
//
//        List<Product> products = Collections.unmodifiableList(Arrays.asList(
//                Product.of(
//                        "black tea",
//                        "1",
//                        "good tea from mia banana",
//                        "sm",
//                        "bm",
//                        images, Boolean.TRUE, 100.0, "mia"),
//                Product.of(
//                        "green tea",
//                        "1",
//                        "standard tea from cenxui banana",
//                        "sm",
//                        "bm",
//                        images, Boolean.TRUE, 100.0, "cenxui"),
//                Product.of(
//                        "woolong tea",
//                        "1",
//                        "woolong tea from cenxui mia",
//                        "sm",
//                        "bm",
//                        images, Boolean.TRUE, 200.0, "cenxui"),
//                Product.of(
//                        "mountain green tea",
//                        "1",
//                        "mountain tea from cenxui mia",
//                        "sm",
//                        "bm",
//                        images, Boolean.TRUE, 200.0, "mia")
//        ));
//
//        for (Product product : products) {
//
//            table.putItem(ItemUtil.getProductItem(product));
//        }
    }

    private void deleteItem() {
        table.deleteItem(Product.PRODUCT_NAME, "black tea", Product.VERSION, 1);
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
                Product.PRODUCT_NAME,          // key attribute name
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
