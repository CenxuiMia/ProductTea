package com.cenxui.tea.app.aws.dynamodb.local.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.*;
import com.cenxui.tea.app.aws.dynamodb.item.ItemOrder;
import com.cenxui.tea.app.aws.dynamodb.item.ItemProduct;
import com.cenxui.tea.app.aws.dynamodb.local.repositories.util.DynamoDBLocalUtil;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.aws.dynamodb.util.ItemUtil;
import com.cenxui.tea.app.aws.dynamodb.util.exception.DuplicateProductException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
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
        createTable();
    }

    @Test
    public void dataLifeCycle() {

        for (int i = 0 ; i <5; i++) {
            putItems();
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        listAllItem();
//        queryOrderIndex();

    }

    private void createTable() {

        AttributeDefinition mail = new AttributeDefinition(Order.MAIL, "S");
        AttributeDefinition time = new AttributeDefinition(Order.TIME, "S");
        AttributeDefinition isPaid = new AttributeDefinition(Order.PHONE, "S");

        Collection<AttributeDefinition> attributeDefinitions =
                Arrays.asList(mail, time, isPaid);


        KeySchemaElement primaryKey = new KeySchemaElement(Order.MAIL, KeyType.HASH);
        KeySchemaElement sortKey = new KeySchemaElement(Order.TIME, KeyType.RANGE);


        Collection<KeySchemaElement> keySchemaElements =
                Arrays.asList(primaryKey, sortKey);

        GlobalSecondaryIndex orderIndex = new GlobalSecondaryIndex()
                .withIndexName("orderIndex")
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(1L)
                        .withWriteCapacityUnits(1L))
                .withProjection(new Projection().withProjectionType(ProjectionType.ALL));

        ArrayList<KeySchemaElement> indexKeySchema = new ArrayList<KeySchemaElement>();

        indexKeySchema.add(new KeySchemaElement()
                .withAttributeName(Order.PHONE)
                .withKeyType(KeyType.HASH));  //Partition key

        orderIndex.setKeySchema(indexKeySchema);

        ProvisionedThroughput provisionedThroughput =
                new ProvisionedThroughput(1l, 1l);

        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName(tableName)
                .withAttributeDefinitions(attributeDefinitions)
                .withKeySchema(keySchemaElements)
                .withGlobalSecondaryIndexes(orderIndex)
                .withProvisionedThroughput(provisionedThroughput);

        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

        table = dynamoDB.createTable(createTableRequest);
    }

    public void queryOrderIndex() {
        System.out.println("================query ordeIndex==========================");

        Index index = table.getIndex("orderIndex");
        index.query(Order.PHONE, "123").forEach(System.out::println);

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
        List<String> products =
                Arrays.asList("green tea = 10",
                        "black tea = 11",
                        "lol tea = 10"
                );

        List<Order> orders = Arrays.asList(
                Order.of(
                        "abc@gmail.com",
                        products,
                        "purchaser",
                        "123",
                        "taipei",
                        "acvb"
                        ,true,
                        true

                ),
                Order.of(
                        "mia@gmail.com",
                         products,
                        "purchaser",
                        "321",
                        "taipei",
                        "acvb"
                        ,true,
                        true
                ),
                Order.of(
                        "123@gmail.com",
                         products,
                        "purchaser",
                        "123",
                        "taipei",
                        "acvb"
                        ,true,
                        true
                )
                ,  Order.of(
                        "cp9@gmail.com",
                        products,
                        "purchaser",
                        "467",
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
        List<Order> orders = new ArrayList<>();
        table.scan().forEach(
                (s) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

                    try {
                        Order order = mapper.readValue(s.toJSON(), ItemOrder.class).getItem();
                        orders.add(order);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );

        System.out.println("=========================orders==============================");
        orders.stream().forEach(System.out::println);
    }


    @After
    public void shutDown() throws Exception {
        if (server != null) {
            server.stop();
        }
    }

}
