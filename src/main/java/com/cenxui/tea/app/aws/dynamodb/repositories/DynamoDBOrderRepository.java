package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.cenxui.tea.app.aws.dynamodb.item.ItemOrder;
import com.cenxui.tea.app.aws.dynamodb.util.ItemUtil;
import com.cenxui.tea.app.aws.dynamodb.util.exception.DuplicateProductException;
import com.cenxui.tea.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class DynamoDBOrderRepository implements OrderRepository {
    private AmazonDynamoDB amazonDynamoDB = DynamoDBManager.getAmazonDynamoDBClient();
    private Table orderTable = DynamoDBManager.getDynamoDB().getTable(DynamoDBConfig.ORDER_TABLE);

    @Override
    public List<Order> listAllOrders() {

        ItemCollection collection = orderTable.scan();

        return getOrders(collection);
    }


    @Override
    public List<Order> listOrderByTMail(String mail) {
        ItemCollection collection = orderTable.query(Order.MAIL, mail);

        return getOrders(collection);
    }

    @Override
    public Order getOrdersByMailAndTime(String mail, String time) {

        return null;
    }

    @Override
    public void addOrder(Order order) {
        try {
            PutItemSpec putItemSpec = new PutItemSpec()
                    .withItem(ItemUtil.getOrderItem(order))
                    .withConditionExpression("attribute_not_exists("+ Order.MAIL + ")");
            orderTable.putItem(putItemSpec);
        } catch (DuplicateProductException e) {
            e.printStackTrace();
        } catch (ConditionalCheckFailedException e) {
            System.out.println("Record already exists in Dynamo DB Table");
        }
    }

    @Override
    public void removeOrder() {

    }

    @Override
    public void updateOrder() {

    }

    private List<Order> getOrders(ItemCollection collection) {
        List<Order> orders = new ArrayList<>();

        collection.forEach(
                (s) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        Order order = mapper.readValue(s.toString(), ItemOrder.class).getItem();
                        orders.add(order);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );

        return Collections.unmodifiableList(orders);
    }

}
