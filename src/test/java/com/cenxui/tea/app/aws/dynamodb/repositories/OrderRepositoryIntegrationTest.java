package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.*;
import com.cenxui.tea.app.aws.dynamodb.util.ItemUtil;
import com.cenxui.tea.app.aws.dynamodb.util.exception.DuplicateProductException;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

@RunWith(JUnit4.class)
public class OrderRepositoryIntegrationTest {

    private Table table;
    private final String tableName = "teaOrder";

    @Before
    public void setUp() {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
                .standard()
                .withRegion(Regions.AP_NORTHEAST_1).build(); // in product state do not input any credential
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        table = dynamoDB.getTable(tableName);
    }

    @Test
    public void dataLifeCycle() {
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
                        false

                ),
                Order.of(
                        "mia@gmail.com",
                        products,
                        "purchaser",
                        "7654321",
                        "taipei",
                        "acvb"
                        ,false,
                        true
                ),
                Order.of(
                        "123@gmail.com",
                        products,
                        "purchaser",
                        "1234567",
                        "taipei",
                        "acvb"
                        ,false,
                        false
                )
                ,
                Order.of(
                        "john@gmail.com",
                        products,
                        "purchaser",
                        "1234567",
                        "kkkk",
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

}

