package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.cenxui.tea.app.aws.dynamodb.exceptions.map.OrderJsonMapException;
import com.cenxui.tea.app.aws.dynamodb.item.ItemOrder;
import com.cenxui.tea.app.aws.dynamodb.util.ItemUtil;
import com.cenxui.tea.app.aws.dynamodb.util.exception.DuplicateProductException;
import com.cenxui.tea.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * todo scan should have some limit
 */

class DynamoDBOrderRepository implements OrderRepository {

    private Table orderTable = DynamoDBManager.getDynamoDB().getTable(DynamoDBConfig.ORDER_TABLE);

    @Override
    public List<Order> getAllOrders() {
        ItemCollection collection = orderTable.scan();
        return mapToOrders(collection);
    }


    @Override
    public List<Order> getOrderByTMail(String mail) {
        ItemCollection collection = orderTable.query(Order.MAIL, mail);

        return mapToOrders(collection);
    }

    @Override
    public List<Order> getAllProcessingOrders() {
        Index index = orderTable.getIndex(DynamoDBConfig.ORDER_PROCESSING_INDEX);
        ItemCollection collection = index.scan();

        return mapToOrders(collection);
    }

    @Override
    public List<Order> getAllShippedOrders() {
        Index index = orderTable.getIndex(DynamoDBConfig.ORDER_SHIPPED_INDEX);
        ItemCollection collection = index.scan();

        return mapToOrders(collection);
    }

    @Override
    public List<Order> getAllPaidOrders() {
        Index index = orderTable.getIndex(DynamoDBConfig.ORDER_PAID_INDEX);
        ItemCollection collection = index.scan();

        return mapToOrders(collection);
    }


    @Override
    public Order getOrdersByMailAndTime(String mail, String time) {
        throw new UnsupportedOperationException("not yet");
        //todo
    }

    private List<Order> mapToOrders(ItemCollection collection) {
        List<Order> orders = new ArrayList<>();

        collection.forEach(
                (s) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    String orderJson = s.toString();
                    try {
                        Order order = mapper.readValue(orderJson, ItemOrder.class).getItem();
                        orders.add(order);
                    } catch (IOException e) {
                        throw new OrderJsonMapException(orderJson);
                    }
                }
        );

        return Collections.unmodifiableList(orders);
    }


    @Override
    public boolean addOrder(Order order) {
        try {
            PutItemSpec putItemSpec = new PutItemSpec()
                    .withItem(ItemUtil.getOrderItem(order))
                    .withConditionExpression("attribute_not_exists("+ Order.MAIL + ")");
            orderTable.putItem(putItemSpec);
            return true;
        } catch (DuplicateProductException e) {
            System.out.println("Product record can not be duplicated "); //todo modify to runtime exception
        } catch (ConditionalCheckFailedException e) {
            System.out.println("Record already exists in Dynamo DB Table"); //todo modify to runtime exception
        }
        return false;
    }

    @Override
    public boolean removeOrder(String mail, String time) {
        //todo
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public Order activeOrder(String mail, String time) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.TIME, time)
                .withUpdateExpression("set " + Order.IS_ACTIVE + "=:ia")
                .withValueMap(new ValueMap().withBoolean( ":ia", true))
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String itemJson = outcome.getItem().toJSON();
        ObjectMapper objectMapper = new ObjectMapper();
        Order order = null;
        try {
            order = objectMapper.readValue(itemJson, Order.class);
        } catch (IOException e) {
            throw new OrderJsonMapException(itemJson);
        }
        return order;
    }

    @Override
    public Order deActiveOrder(String mail, String time) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.TIME, time)
                .withConditionExpression("attribute_not_exists(" + Order.PAID_DATE + ")")
                .withUpdateExpression("remove " + Order.IS_ACTIVE)
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String itemJson = outcome.getItem().toJSON();
        ObjectMapper objectMapper = new ObjectMapper();
        Order order = null;
        try {
             order = objectMapper.readValue(itemJson, Order.class);
        } catch (IOException e) {
            throw new OrderJsonMapException(itemJson);
        }
        return order;
    }

    @Override
    public Order payOrder(String mail, String time) {
        String date = LocalDate.now().toString();

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.TIME, time)
                .withUpdateExpression("set " + Order.PAID_DATE + "=:pa,"+ Order.PROCESS_DATE+ "=:pr")
                .withConditionExpression("attribute_exists(" + Order.IS_ACTIVE +")")
                .withValueMap(new ValueMap().withString(":pa" , date).withString(":pr", date))
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String itemJson = outcome.getItem().toJSON();
        ObjectMapper objectMapper = new ObjectMapper();
        Order order;
        try {
            order = objectMapper.readValue(itemJson, Order.class);
        } catch (IOException e) {
            throw new OrderJsonMapException(itemJson);
        }
        return order;
    }

    @Override
    public Order dePayOrder(String mail, String time) {

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.TIME, time)
                .withUpdateExpression("remove " + Order.PAID_DATE+ "," + Order.PROCESS_DATE)
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String itemJson = outcome.getItem().toJSON();
        ObjectMapper objectMapper = new ObjectMapper();
        Order order;
        try {
            order = objectMapper.readValue(itemJson, Order.class);
        } catch (IOException e) {
            throw new OrderJsonMapException(itemJson);
        }
        return order;
    }

    @Override
    public Order shipOrder(String mail, String time) {
        String date = LocalDate.now().toString();

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.TIME, time)
                .withUpdateExpression("set " + Order.SHIP_DATE+ "=:sh" + " remove " + Order.PROCESS_DATE)
                .withValueMap(new ValueMap().withString(":sh", date))
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String itemJson = outcome.getItem().toJSON();
        ObjectMapper objectMapper = new ObjectMapper();
        Order order;
        try {
            order = objectMapper.readValue(itemJson, Order.class);
        } catch (IOException e) {
            throw new OrderJsonMapException(itemJson);
        }
        return order;
    }

    @Override
    public Order deShipOrder(String mail, String time) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.TIME, time)
                .withUpdateExpression("set " + Order.PROCESS_DATE+ "=" + Order.PAID_DATE +
                        " remove " + Order.SHIP_DATE)
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String itemJson = outcome.getItem().toJSON();
        ObjectMapper objectMapper = new ObjectMapper();
        Order order;
        try {
            order = objectMapper.readValue(itemJson, Order.class);
        } catch (IOException e) {
            throw new OrderJsonMapException(itemJson);
        }
        return order;
    }

}
