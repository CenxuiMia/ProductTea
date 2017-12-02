package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.cenxui.tea.app.aws.dynamodb.exceptions.order.*;
import com.cenxui.tea.app.aws.dynamodb.exceptions.map.order.OrderJsonMapException;
import com.cenxui.tea.app.repositories.order.report.CashReport;
import com.cenxui.tea.app.repositories.order.*;
import com.cenxui.tea.app.aws.dynamodb.util.ItemUtil;
import com.cenxui.tea.app.repositories.order.report.Receipt;
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
    public Orders getAllOrders() {
        return getAllOrders(null, null, null);
    }

    @Override
    public Orders getAllOrders(String mail, String orderDateTime, Integer limit) {
        ScanSpec scanSpec = new ScanSpec();

        if (limit != null) {
             scanSpec.withMaxResultSize(limit);
        }

        if (mail != null && orderDateTime != null) {
             scanSpec.withExclusiveStartKey(Order.MAIL, mail, Order.ORDER_DATE_TIME, orderDateTime);
        }

        ItemCollection<ScanOutcome> collection = orderTable.scan(scanSpec);
        List<Order> orders = mapScanOutcomeToOrders(collection);
        OrderKey orderKey = getScanOutcomeLastKey(collection);
        return Orders.of(orders, orderKey);
    }

    @Override
    public Orders getAllActiveOrders() {
        return getAllActiveOrders(null, null, null);
    }

    @Override
    public Orders getAllActiveOrders(String mail, String orderDateTime, Integer limit) {
        ScanSpec scanSpec = new ScanSpec()
                .withFilterExpression("isActive = :v")
                .withValueMap(new ValueMap().withBoolean(":v", true));

        if (limit != null) {
            scanSpec.withMaxResultSize(limit);
        }

        if (mail != null && orderDateTime != null) {
            scanSpec.withExclusiveStartKey(Order.MAIL, mail, Order.ORDER_DATE_TIME, orderDateTime);
        }

        ItemCollection<ScanOutcome> collection = orderTable.scan(scanSpec);
        List<Order> orders = mapScanOutcomeToOrders(collection);
        OrderKey orderKey = getScanOutcomeLastKey(collection);
        return Orders.of(orders, orderKey);
    }

    @Override
    public Orders getAllPaidOrders() {
        return getAllPaidOrders(null, null, null);
    }

    @Override
    public Orders getAllPaidOrders(String paidDate, String paidTime, Integer limit) {
        ScanSpec scanSpec = new ScanSpec();

        if (limit != null ) {
            scanSpec.withMaxResultSize(limit);
        }

        if (paidTime != null) {
            scanSpec.withExclusiveStartKey(Order.PAID_TIME, paidTime); //todo
        }

        Index index = orderTable.getIndex(paidIndex);

        ItemCollection<ScanOutcome> collection = index.scan(scanSpec);

        List<Order> orders = mapScanOutcomeToOrders(collection);

        PaidOrderKey key = getPaidIndexScanOutcomeLastKey(collection);

        return Orders.of(orders, key);
    }

    @Override
    public Orders getAllProcessingOrders() {
        return getAllProcessingOrders(null, null);
    }

    @Override
    public Orders getAllProcessingOrders(String processingDate, Integer limit) {
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

        return Orders.of(orders, key);
    }

    @Override
    public Orders getAllShippedOrders() {
        return getAllShippedOrders(null, null, null);
    }

    @Override
    public Orders getAllShippedOrders(String shippedDate, String shippedTime, Integer limit) {
        ScanSpec scanSpec = new ScanSpec(); //todo

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

        return Orders.of(orders, key);
    }

    @Override
    public Orders getOrdersByMail(String mail) {
        QuerySpec spec = new QuerySpec()
                .withHashKey(Order.MAIL, mail);

        ItemCollection<QueryOutcome> collection = orderTable.query(spec);

        List<Order> orders = mapQueryOutcomeToOrders(collection);
        OrderKey orderKey = getQueryOutcomeLastKey(collection);
        return Orders.of(orders, orderKey);
    }


    @Override
    public Order getOrdersByMailAndTime(String mail, String orderDateTime) {
        QuerySpec spec = new QuerySpec()
                .withHashKey(Order.MAIL, mail)
                .withRangeKeyCondition(new RangeKeyCondition(Order.ORDER_DATE_TIME).eq(orderDateTime));
        ItemCollection<QueryOutcome> collection = orderTable.query(spec);
        List<Order> orders = mapQueryOutcomeToOrders(collection);

        if (orders.size() == 1) {
            return orders.get(0);
        }

        return null;
    }


    @Override
    public Order addOrder(Order order) {
        PutItemSpec putItemSpec = new PutItemSpec()
                .withItem(ItemUtil.getOrderItem(order))
                .withConditionExpression("attribute_not_exists("+ Order.MAIL + ")");
        try {
            orderTable.putItem(putItemSpec);
            return order;
        } catch (ConditionalCheckFailedException e) {
            throw new OrderCannotAddException(order);
        }
    }

    @Override
    public boolean deleteOrder(String mail, String orderDateTime) {
        //todo
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public Order activeOrder(String mail, String orderDateTime) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.ORDER_DATE_TIME, orderDateTime)
                .withConditionExpression("attribute_not_exists(" + Order.IS_ACTIVE +")")
                .withUpdateExpression("set " + Order.IS_ACTIVE + "=:ia")
                .withValueMap(new ValueMap().withBoolean( ":ia", true))
                .withReturnValues(ReturnValue.ALL_NEW);

        try {
            UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
            String orderJson = outcome.getItem().toJSON();
            return getOrder(orderJson);
        }catch (ConditionalCheckFailedException e) {
            throw new OrderCannotActiveException(mail, orderDateTime);
        }
    }

    @Override
    public Order deActiveOrder(String mail, String dateTime) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.ORDER_DATE_TIME, dateTime)
                .withConditionExpression(
                                "attribute_not_exists(" + Order.PAID_DATE + ")" +
                                " and attribute_not_exists(" + Order.PAID_TIME + ")" +
                                " and attribute_not_exists(" + Order.PROCESSING_DATE + ")" +
                                " and attribute_not_exists(" + Order.SHIPPED_DATE + ")" +
                                " and attribute_not_exists(" + Order.SHIPPED_TIME + ")" +
                                " and attribute_exists(" + Order.IS_ACTIVE + ")")
                .withUpdateExpression("remove " + Order.IS_ACTIVE)
                .withReturnValues(ReturnValue.ALL_NEW);

        try {
            UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
            String orderJson = outcome.getItem().toJSON();
            return getOrder(orderJson);
        }catch (ConditionalCheckFailedException e) {
            throw new OrderCannotDeActiveException(mail, dateTime);
        }
    }

    @Override
    public Order payOrder(String mail, String orderDateTime) {
        LocalDateTime now =  LocalDateTime.now();
        String paidDate = now.toLocalDate().toString();
        String paidTime = now.toLocalTime().toString();
        return payOrder(mail, orderDateTime, paidDate, paidTime);
    }

    @Override
    public Order payOrder(String mail, String orderDateTime, String paidDate, String paidTime) {
        String processingDate = LocalDateTime.now().toString();

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.ORDER_DATE_TIME, orderDateTime)
                .withUpdateExpression(
                        "set " + Order.PAID_DATE + "=:pa_d," +
                                Order.PAID_TIME + "=:pa_t,"+
                                Order.PROCESSING_DATE+ "=:pr_d")
                .withConditionExpression(
                        "attribute_exists(" + Order.IS_ACTIVE +")" +
                                "and attribute_not_exists(" + Order.PAID_DATE + ")" +
                                "and attribute_not_exists(" + Order.PAID_TIME + ")" +
                                "and attribute_not_exists(" + Order.PROCESSING_DATE + ")" +
                                "and attribute_not_exists(" + Order.SHIPPED_DATE + ")" +
                                "and attribute_not_exists(" + Order.SHIPPED_TIME + ")")
                .withValueMap(new ValueMap()
                                .withString(":pa_d", paidDate)
                                .withString(":pa_t" , paidTime)
                                .withString(":pr_d", processingDate))
                .withReturnValues(ReturnValue.ALL_NEW);

        try {
            UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
            String orderJson = outcome.getItem().toJSON();
            return getOrder(orderJson);
        }catch (ConditionalCheckFailedException e) {
            throw new OrderCannotPayException(mail, orderDateTime, paidDate, paidTime);
        }
    }

    @Override
    public Order dePayOrder(String mail, String orderDateTime) {

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.ORDER_DATE_TIME, orderDateTime)
                .withConditionExpression(
                                        "attribute_exists(" + Order.IS_ACTIVE +")" +
                                        " and attribute_exists(" + Order.PAID_DATE +")" +
                                        " and attribute_exists(" + Order.PAID_TIME +")" +
                                        " and attribute_exists(" + Order.PROCESSING_DATE + ")" +
                                        " and attribute_not_exists(" + Order.SHIPPED_TIME + ")" +
                                        " and attribute_not_exists(" + Order.SHIPPED_TIME + ")")
                .withUpdateExpression("remove " + Order.PAID_DATE + "," + Order.PAID_TIME+ "," + Order.PROCESSING_DATE)
                .withReturnValues(ReturnValue.ALL_NEW);

        try {
            UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
            String orderJson = outcome.getItem().toJSON();
            return getOrder(orderJson);
        }catch (ConditionalCheckFailedException e) {
            throw new OrderCannotDePayExcetion(mail, orderDateTime);
        }
    }

    @Override
    public Order shipOrder(String mail, String orderDateTime) {
        LocalDateTime now =  LocalDateTime.now();
        String shippedDate = now.toLocalDate().toString();
        String shippedTime = now.toLocalTime().toString();
        return shipOrder(mail, orderDateTime, shippedDate, shippedTime);
    }

    @Override
    public Order shipOrder(String mail, String orderDateTime, String shippedDate, String shippedTime) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withConditionExpression(
                                "attribute_exists(" + Order.IS_ACTIVE +")" +
                                " and attribute_exists(" + Order.PAID_TIME +")" +
                                " and attribute_exists(" + Order.PAID_TIME +")" +
                                " and attribute_exists(" + Order.PROCESSING_DATE +")" +
                                " and attribute_not_exists(" + Order.SHIPPED_DATE + ")" +
                                " and attribute_not_exists(" + Order.SHIPPED_TIME + ")")
                .withPrimaryKey(Order.MAIL, mail, Order.ORDER_DATE_TIME, orderDateTime)
                .withUpdateExpression(
                        "set "+ Order.SHIPPED_DATE+ "=:sh_d" + "," + Order.SHIPPED_TIME + "=:sh_t" +
                                " remove " + Order.PROCESSING_DATE)
                .withValueMap(new ValueMap().withString(":sh_d", shippedDate).withString(":sh_t", shippedTime))
                .withReturnValues(ReturnValue.ALL_NEW);

        try {
            UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
            String orderJson = outcome.getItem().toJSON();
            return getOrder(orderJson);
        }catch (ConditionalCheckFailedException e) {
            throw new OrderCannotShipException(mail, orderDateTime, shippedTime);
        }

    }

    @Override
    public Order deShipOrder(String mail, String orderDateTime) {

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.ORDER_DATE_TIME, orderDateTime)
                .withConditionExpression(
                                "attribute_exists(" + Order.IS_ACTIVE + ")" +
                                "and attribute_exists(" + Order.PAID_DATE + ")" +
                                "and attribute_exists(" + Order.PAID_TIME + ")" +
                                "and attribute_not_exists(" + Order.PROCESSING_DATE + ")" +
                                "and attribute_exists(" + Order.SHIPPED_DATE + ")" +
                                "and attribute_exists(" + Order.SHIPPED_TIME + ")")
                .withUpdateExpression("set " + Order.PROCESSING_DATE + "="+ Order.PAID_DATE +
                        " remove " + Order.SHIPPED_DATE + "," + Order.SHIPPED_TIME)
                .withReturnValues(ReturnValue.ALL_NEW);
        try {
            UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
            String orderJson = outcome.getItem().toJSON();
            return getOrder(orderJson);
        }catch (ConditionalCheckFailedException e) {
            throw new OrderCannotDeShipException(mail, orderDateTime);
        }
    }

    @Override
    public CashReport getCashAllReport() {
        ScanSpec scanSpec = new ScanSpec()
                .withProjectionExpression(
                        Order.MAIL + "," +
                        Order.ORDER_DATE_TIME + "," +
                        Order.PAYMENT_METHOD + "," +
                        Order.PRICE
                );
        ItemCollection<ScanOutcome> collection =
                orderTable.getIndex(paidIndex).scan(scanSpec);

        List<Order> orders = mapScanOutcomeToOrders(collection);

        Double revenue = orders.stream().mapToDouble(s -> s.getPrice()).sum();

        final List<Receipt> receipts = new ArrayList<>();

        orders.forEach((s)-> {
            receipts.add(
                    Receipt.of(
                            OrderKey.of(
                                    s.getMail(),
                                    s.getOrderDateTime()),
                            s.getPaymentMethod(),
                            s.getPrice()));
        });

        PaidOrderKey lastKey = getPaidIndexScanOutcomeLastKey(collection);

        return CashReport.of(receipts, revenue, lastKey);
    }

    @Override
    public CashReport getDailyCashReport(String paidDate) {
        //todo
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public CashReport getRangeCashReport(String fromPaidDate, String toPaidDate) {
        //todo
        throw new UnsupportedOperationException("not yet");
    }

    private OrderKey getScanOutcomeLastKey(ItemCollection<ScanOutcome> collection) {
        ScanOutcome scanOutcome = collection.getLastLowLevelResult();
        Map<String, AttributeValue> lastKeyEvaluated = scanOutcome.getScanResult().getLastEvaluatedKey();

        OrderKey orderKey = null;

        if (lastKeyEvaluated != null) {//null if it is last one
            orderKey = OrderKey.of(
                    lastKeyEvaluated.get(Order.MAIL).getS(), lastKeyEvaluated.get(Order.ORDER_DATE_TIME).getS());
        }

        return orderKey;
    }

    private OrderKey getQueryOutcomeLastKey(ItemCollection<QueryOutcome> collection) {
        QueryOutcome queryOutcome = collection.getLastLowLevelResult();
        Map<String, AttributeValue> lastKeyEvaluated = queryOutcome.getQueryResult().getLastEvaluatedKey();

        OrderKey orderKey = null;

        if (lastKeyEvaluated != null) {//null if it is last one
            orderKey = OrderKey.of(
                    lastKeyEvaluated.get(Order.MAIL).getS(), lastKeyEvaluated.get(Order.ORDER_DATE_TIME).getS());
        }

        return orderKey;
    }

    private PaidOrderKey getPaidIndexScanOutcomeLastKey(ItemCollection<ScanOutcome> collection) {
        ScanOutcome scanOutcome = collection.getLastLowLevelResult();
        Map<String, AttributeValue> lastKeyEvaluated = scanOutcome.getScanResult().getLastEvaluatedKey();

        PaidOrderKey paidOrderKey = null;

        if (lastKeyEvaluated != null) {//null if it is last one
            paidOrderKey = PaidOrderKey.of(
                    lastKeyEvaluated.get(Order.PAID_DATE).getS()
                    ,lastKeyEvaluated.get(Order.PAID_TIME).getS());
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
                    lastKeyEvaluated.get(Order.SHIPPED_DATE).getS(),
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
