package com.cenxui.tea.app.integration.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.*;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.integration.repositories.util.DynamoDBLocalUtil;
import com.cenxui.tea.aws.dynamodb.util.ItemUtil;
import com.cenxui.tea.aws.dynamodb.util.exception.DuplicateProductException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

@RunWith(JUnit4.class)
public class OrderRepositoryIntegrationTest {

    private DynamoDBProxyServer server;
    private AmazonDynamoDB amazonDynamoDB;
    private Table table;
    private String tableName = "TeaOrder";

    @Before
    public void setUp() {
        server = DynamoDBLocalUtil.getDynamoDBProxyServerInMemory();
        amazonDynamoDB = DynamoDBLocalUtil.getAmazonDynamoDB();

    }

    @Test
    public void dataLifeCycle() {
        createTable();
        for (int i = 0 ; i <5; i++) {
            putItems();
            listAllItem();
            System.out.println("--------------------------Items----------------------------");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queryItems();

    }

    private void createTable() {

        AttributeDefinition mail = new AttributeDefinition(Order.MAIL, "S");
        AttributeDefinition time = new AttributeDefinition(Order.TIME, "S");


        Collection<AttributeDefinition> attributeDefinitions =
                Arrays.asList(mail, time);

        KeySchemaElement primaryKey = new KeySchemaElement(Order.MAIL, KeyType.HASH);
        KeySchemaElement sortKey = new KeySchemaElement(Order.TIME, KeyType.RANGE);


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

    private void queryItems() {
        table.query(Order.MAIL, "abc@gmail.com").forEach(System.out::println);
        System.out.println("------------------------abc@gmail.com-------------------------");
        table.query(Order.MAIL, "mia@gmail.com").forEach(System.out::println);
        System.out.println("------------------------mia@gmail.com-------------------------");
        table.query(Order.MAIL, "123@gmail.com").forEach(System.out::println);
        System.out.println("------------------------123@gmail.com-------------------------");
    }

    private void putItems() {
        HashMap<Product, Integer> map = new HashMap<>();
        List<Map<Product, Integer>> products =
                Arrays.asList(map);

        List<Order> orders = Arrays.asList(
                Order.of(
                        "abc@gmail.com",
                        products,
                        "purchaser",
                        "1234567",
                        "taipei",
                        "acvb"
                        ,true,
                        true

                ),
                Order.of(
                        "mia@gmail.com",
                         products,
                        "purchaser",
                        "7654321",
                        "taipei",
                        "acvb"
                        ,true,
                        true
                ),
                Order.of(
                        "123@gmail.com",
                         products,
                        "purchaser",
                        "1234567",
                        "taipei",
                        "acvb"
                        ,true,
                        true
                )

        );

        for (Order order : orders) {

            try {
                PutItemSpec putItemSpec = new PutItemSpec()
                        .withItem(ItemUtil.getOrderItem(order))
                        .withConditionExpression("attribute_not_exists("+ Order.MAIL + ")");
                table.putItem(putItemSpec);
            } catch (DuplicateProductException e) {
                e.printStackTrace();
            } catch (ConditionalCheckFailedException e) {
                System.out.println("Record already exists in Dynamo DB Table");
            }
        }




    }

    private void listAllItem() {
        ItemCollection collection = table.scan();
        collection.forEach(System.out::println);
    }


    @After
    public void shutDown() throws Exception {
        if (server != null) {
            server.stop();
        }
    }

}
