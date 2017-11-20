package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.*;
import com.cenxui.tea.app.aws.dynamodb.local.repositories.util.TestData;
import com.cenxui.tea.app.aws.dynamodb.util.ItemUtil;
import com.cenxui.tea.app.aws.dynamodb.util.exception.DuplicateProductException;
import com.cenxui.tea.app.repositories.order.Order;
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
//        for (int i = 0 ; i <3; i++) {
//            putItems();
//            listAllItem();
//            System.out.println("--------------------------Items----------------------------");
//            try {
//                Thread.sleep(2000L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        queryItems();

        queryIndex();
    }

    private void queryIndex() {
        System.out.println("==========================queryOrderIndex====================");
        Index orderIndex = table.getIndex("orderIndex");

        String date1 = "2017-11-03";
        System.out.println("=========================" + date1 + "=========================");
        orderIndex.query(Order.PAID_DATE, date1).forEach(System.out::println);
        String date2 = "2017-11-01";
        System.out.println("=========================" + date2 + "=========================");
        orderIndex.query(Order.PAID_DATE, date2).forEach(System.out::println);
        String date3 = "2017-11-02";
        System.out.println("=========================" + date3 + "=========================");
        orderIndex.query(Order.PAID_DATE, date3).forEach(System.out::println);
        String date4 = "2017-11-06";
        System.out.println("=========================" + date4 + "=========================");
        orderIndex.query(Order.PAID_DATE, date4).forEach(System.out::println);

        System.out.println("=========================2017-11-03 15:34:58=========================");

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


        List<Order> orders = TestData.getOrders();

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

