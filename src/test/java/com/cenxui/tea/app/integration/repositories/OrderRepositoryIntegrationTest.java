package com.cenxui.tea.app.integration.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.*;
import com.cenxui.tea.app.integration.repositories.catagory.Product;
import com.cenxui.tea.app.integration.repositories.order.Order;
import com.cenxui.tea.app.integration.repositories.util.DynamoDBLocalUtil;
import com.cenxui.tea.dynamodb.util.ItemUtil;
import com.cenxui.tea.dynamodb.util.exception.DuplicateProductException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

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
        createTable();
        putData();
        listAllItem();
    }

    private void createTable() {

        AttributeDefinition name = new AttributeDefinition(Order.TIME, "S");
        AttributeDefinition version = new AttributeDefinition(Order.MAIL, "S");

        Collection<AttributeDefinition> attributeDefinitions =
                Arrays.asList(name, version);

        KeySchemaElement primaryKey = new KeySchemaElement(Order.TIME, KeyType.HASH);
        KeySchemaElement sortKey = new KeySchemaElement(Order.MAIL, KeyType.RANGE);


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

    private void putData() {
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

                ),
                Order.of(
                        "abc@gmail.com",
                         products,
                        "purchaser",
                        "7654321",
                        "taipei",
                        "acvb"

                ),
                Order.of(
                        "123@gmail.com",
                         products,
                        "purchaser",
                        "1234567",
                        "taipei",
                        "acvb"

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
