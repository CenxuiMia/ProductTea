package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.cenxui.tea.app.aws.dynamodb.exceptions.map.order.OrderAlreadyExistException;
import com.cenxui.tea.app.aws.dynamodb.exceptions.map.order.OrderJsonMapException;
import com.cenxui.tea.app.repositories.order.*;
import com.cenxui.tea.app.aws.dynamodb.util.ItemUtil;
import com.cenxui.tea.app.util.JsonUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class DynamoDBOrderRepository implements OrderRepository {

    private final Table orderTable;
    private final String paidIndex;
    private final String processingIndex;
    private final String shippedIndex;


    DynamoDBOrderRepository(
            Table orderTable,
            String paidIndex,
            String processingIndex,
            String shippedIndex) {
        this.orderTable =  orderTable;
        this.paidIndex = paidIndex;
        this.processingIndex = processingIndex;
        this.shippedIndex = shippedIndex;
    }

    @Override
    public OrderResult getAllOrders() {
        return getAllOrders(null, null, null);
    }

    @Override
    public OrderResult getAllOrders(Integer limit, String mail, String time) {
        ScanSpec scanSpec = new ScanSpec();

        if (limit != null) {
             scanSpec.withMaxResultSize(limit);
        }

        if (mail != null && time != null) {
             scanSpec.withExclusiveStartKey(Order.MAIL, mail, Order.TIME, time);
        }

        ItemCollection<ScanOutcome> collection = orderTable.scan(scanSpec);
        List<Order> orders = mapScanOutcomeToOrders(collection);
        OrderKey orderKey = getScanOutcomeLastKey(collection);
        return OrderResult.of(orders, orderKey);
    }

    @Override
    public OrderResult getAllPaidOrders() {
        return getAllPaidOrders(null, null);
    }

    @Override
    public OrderResult getAllPaidOrders(Integer limit, String paidTime) {
        ScanSpec scanSpec = new ScanSpec();

        if (limit != null ) {
            scanSpec.withMaxResultSize(limit);
        }

        if (paidTime != null) {
            scanSpec.withExclusiveStartKey(Order.PAID_TIME, paidTime);
        }

        Index index = orderTable.getIndex(paidIndex);

        ItemCollection<ScanOutcome> collection = index.scan(scanSpec);

        List<Order> orders = mapScanOutcomeToOrders(collection);

        PaidOrderKey key = getPaidIndexScanOutcomeLastKey(collection);

        return OrderResult.of(orders, key);
    }

    @Override
    public OrderResult getAllProcessingOrders() {
        return getAllProcessingOrders(null, null);
    }

    @Override
    public OrderResult getAllProcessingOrders(Integer limit, String processingDate) {
        ScanSpec scanSpec = new ScanSpec();

        if (limit != null ) {
            scanSpec.withMaxResultSize(limit);
        }

        if (processingDate != null) {
            scanSpec.withExclusiveStartKey(Order.PROCESSING_DATE, processingDate);
        }

        Index index = orderTable.getIndex(processingIndex);

        ItemCollection<ScanOutcome> collection = index.scan(scanSpec);

        List<Order> orders = mapScanOutcomeToOrders(collection);

        ProcessingOrderKey key = getProcessingIndexScanOutcomeLastKey(collection);

        return OrderResult.of(orders, key);
    }

    @Override
    public OrderResult getAllShippedOrders() {
        return getAllShippedOrders(null, null);
    }

    @Override
    public OrderResult getAllShippedOrders(Integer limit, String shippedTime) {
        ScanSpec scanSpec = new ScanSpec();

        if (limit != null ) {
            scanSpec.withMaxResultSize(limit);
        }

        if (shippedTime != null) {
            scanSpec.withExclusiveStartKey(Order.SHIPPED_TIME, shippedTime);
        }

        Index index = orderTable.getIndex(shippedIndex);

        ItemCollection<ScanOutcome> collection = index.scan(scanSpec);

        List<Order> orders = mapScanOutcomeToOrders(collection);

        ShippedOrderKey key = getShippedIndexScanOutcomeLastKey(collection);

        return OrderResult.of(orders, key);
    }

    @Override
    public OrderResult getOrdersByMail(String mail) {
        QuerySpec spec = new QuerySpec()
                .withHashKey(Order.MAIL, mail);

        ItemCollection<QueryOutcome> collection = orderTable.query(spec);

        List<Order> orders = mapQueryOutcomeToOrders(collection);
        OrderKey orderKey = getQueryOutcomeLastKey(collection);
        return OrderResult.of(orders, orderKey);
    }




    @Override
    public Order getOrdersByMailAndTime(String mail, String time) {
        QuerySpec spec = new QuerySpec()
                .withHashKey(Order.MAIL, mail)
                .withRangeKeyCondition(new RangeKeyCondition(Order.TIME).eq(time));
        ItemCollection<QueryOutcome> collection = orderTable.query(spec);
        List<Order> orders = mapQueryOutcomeToOrders(collection);

        if (orders.size() == 1) {
            return orders.get(0);
        }

        return null;
    }


    @Override
    public Order addOrder(Order order) {

        try {
            PutItemSpec putItemSpec = new PutItemSpec()
                    .withItem(ItemUtil.getOrderItem(order))
                    .withConditionExpression("attribute_not_exists("+ Order.MAIL + ")");
            orderTable.putItem(putItemSpec);
        } catch (ConditionalCheckFailedException e) {
            throw new OrderAlreadyExistException("order exists cannot add the same order");
        }
        return order;
    }

    @Override
    public boolean deleteOrder(String mail, String time) {
        //todo
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public Order activeOrder(String mail, String time) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.TIME, time)
                .withConditionExpression("attribute_not_exists" + Order.IS_ACTIVE +")")
                .withUpdateExpression("set " + Order.IS_ACTIVE + "=:ia")
                .withValueMap(new ValueMap().withBoolean( ":ia", true))
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String orderJson = outcome.getItem().toJSON();

        return getOrder(orderJson);
    }

    @Override
    public Order deActiveOrder(String mail, String time) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.TIME, time)
                .withConditionExpression(
                        "attribute_not_exists(" + Order.PAID_TIME + ")" +
                        " and attribute_exists(" + Order.IS_ACTIVE + ")")
                .withUpdateExpression("remove " + Order.IS_ACTIVE)
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String orderJson = outcome.getItem().toJSON();

        return getOrder(orderJson);
    }

    @Override
    public Order payOrder(String mail, String time) {
        String paidTime = LocalDateTime.now().toString();
        return payOrder(mail, time, paidTime);
    }

    @Override
    public Order payOrder(String mail, String time, String paidTime) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.TIME, time)
                .withUpdateExpression("set " + Order.PAID_TIME + "=:pa,"+ Order.PROCESSING_DATE+ "=:pr")
                .withConditionExpression(
                        "attribute_exists(" + Order.IS_ACTIVE +")" +
                                "add attribute_not_exists(" + Order.PAID_TIME + ")")
                .withValueMap(
                        new ValueMap().withString(":pa" , paidTime).withString(":pr", paidTime))
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String orderJson = outcome.getItem().toJSON();

        return getOrder(orderJson);
    }

    @Override
    public Order dePayOrder(String mail, String time) {

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.TIME, time)
                .withConditionExpression("attribute_exists(" + Order.PAID_TIME +")")
                .withUpdateExpression("remove " + Order.PAID_TIME+ "," + Order.PROCESSING_DATE)
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String orderJson = outcome.getItem().toJSON();

        return getOrder(orderJson);
    }

    @Override
    public Order shipOrder(String mail, String time) {
        String shippedTime = LocalDateTime.now().toString();
        return shipOrder(mail, time, shippedTime);
    }

    @Override
    public Order shipOrder(String mail, String time, String shippedTime) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withConditionExpression(
                        "attribute_exists(" + Order.PAID_TIME +")" +
                                "add attribute_not_exists(" + Order.SHIPPED_TIME)
                .withPrimaryKey(Order.MAIL, mail, Order.TIME, time)
                .withUpdateExpression("set " + Order.SHIPPED_TIME+ "=:sh" + " remove " + Order.PROCESSING_DATE)
                .withValueMap(new ValueMap().withString(":sh", shippedTime))
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String orderJson = outcome.getItem().toJSON();

        return getOrder(orderJson);
    }

    @Override
    public Order deShipOrder(String mail, String time) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.TIME, time)
                .withConditionExpression("attribute_exists(" + Order.SHIPPED_TIME + ")")
                .withUpdateExpression("set " + Order.PROCESSING_DATE+ "=" + Order.PAID_TIME +
                        " remove " + Order.SHIPPED_TIME)
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String orderJson = outcome.getItem().toJSON();


        return getOrder(orderJson);
    }

    private OrderKey getScanOutcomeLastKey(ItemCollection<ScanOutcome> collection) {
        ScanOutcome scanOutcome = collection.getLastLowLevelResult();
        Map<String, AttributeValue> lastKeyEvaluated = scanOutcome.getScanResult().getLastEvaluatedKey();

        OrderKey orderKey = null;

        if (lastKeyEvaluated != null) {//null if it is last one
            orderKey = OrderKey.of(
                    lastKeyEvaluated.get(Order.MAIL).getS(), lastKeyEvaluated.get(Order.TIME).getS());
        }

        return orderKey;
    }

    private OrderKey getQueryOutcomeLastKey(ItemCollection<QueryOutcome> collection) {
        QueryOutcome queryOutcome = collection.getLastLowLevelResult();
        Map<String, AttributeValue> lastKeyEvaluated = queryOutcome.getQueryResult().getLastEvaluatedKey();

        OrderKey orderKey = null;

        if (lastKeyEvaluated != null) {//null if it is last one
            orderKey = OrderKey.of(
                    lastKeyEvaluated.get(Order.MAIL).getS(), lastKeyEvaluated.get(Order.TIME).getS());
        }

        return orderKey;
    }

    private PaidOrderKey getPaidIndexScanOutcomeLastKey(ItemCollection<ScanOutcome> collection) {
        ScanOutcome scanOutcome = collection.getLastLowLevelResult();
        Map<String, AttributeValue> lastKeyEvaluated = scanOutcome.getScanResult().getLastEvaluatedKey();

        PaidOrderKey paidOrderKey = null;

        if (lastKeyEvaluated != null) {//null if it is last one
            paidOrderKey = PaidOrderKey.of(
                    lastKeyEvaluated.get(Order.PAID_TIME).getS());
        }

        return paidOrderKey;
    }

    private ProcessingOrderKey getProcessingIndexScanOutcomeLastKey(ItemCollection<ScanOutcome> collection) {
        ScanOutcome scanOutcome = collection.getLastLowLevelResult();
        Map<String, AttributeValue> lastKeyEvaluated = scanOutcome.getScanResult().getLastEvaluatedKey();

        ProcessingOrderKey processingOrderKey = null;

        if (lastKeyEvaluated != null) {//null if it is last one
            processingOrderKey = ProcessingOrderKey.of(
                    lastKeyEvaluated.get(Order.PROCESSING_DATE).getS());
        }

        return processingOrderKey;
    }

    private ShippedOrderKey getShippedIndexScanOutcomeLastKey(ItemCollection<ScanOutcome> collection) {
        ScanOutcome scanOutcome = collection.getLastLowLevelResult();
        Map<String, AttributeValue> lastKeyEvaluated = scanOutcome.getScanResult().getLastEvaluatedKey();

        ShippedOrderKey shippedOrderKey = null;

        if (lastKeyEvaluated != null) {//null if it is last one
            shippedOrderKey = ShippedOrderKey.of(
                    lastKeyEvaluated.get(Order.SHIPPED_TIME).getS());
        }

        return shippedOrderKey;
    }


    private List<Order> mapScanOutcomeToOrders(ItemCollection<ScanOutcome> collection) {
        List<Order> orders = new ArrayList<>();

        collection.forEach(
                (s) -> {
                    orders.add(getOrder(s.toJSON()));
                }
        );

        return Collections.unmodifiableList(orders);
    }

    private List<Order> mapQueryOutcomeToOrders(ItemCollection<QueryOutcome> collection) {
        List<Order> orders = new ArrayList<>();

        collection.forEach(
                (s) -> {
                    orders.add(getOrder(s.toJSON()));
                }
        );

        return Collections.unmodifiableList(orders);
    }

    private Order getOrder(String orderJson) {
        Order order;

        try {
            order = JsonUtil.mapToOrder(orderJson);
        }catch (Exception e) {
            throw new OrderJsonMapException(orderJson);
        }

        return  order;
    }

}
