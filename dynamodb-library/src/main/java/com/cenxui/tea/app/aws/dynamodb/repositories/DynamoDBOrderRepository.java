package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.cenxui.tea.app.aws.dynamodb.exceptions.client.order.*;
import com.cenxui.tea.app.aws.dynamodb.exceptions.client.map.order.OrderJsonMapException;
import com.cenxui.tea.app.repositories.order.CashReport;
import com.cenxui.tea.app.repositories.order.*;
import com.cenxui.tea.app.aws.dynamodb.util.ItemUtil;
import com.cenxui.tea.app.util.JsonUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

class DynamoDBOrderRepository implements OrderRepository {

    private final Table orderTable;
    private final String paidIndex;
    private final String processingIndex;
    private final String shippedIndex;

    private final Integer max = Integer.MAX_VALUE; //todo

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

        if (limit != null && limit > 0) {
             scanSpec.withMaxResultSize(limit);
        }else {
            scanSpec.withMaxResultSize(max);
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

        if (limit != null && limit > 0) {
            scanSpec.withMaxResultSize(limit);
        }else {
            scanSpec.withMaxResultSize(max);
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
        return getAllPaidOrders(null, null);
    }

    @Override
    public Orders getAllPaidOrders(OrderPaidLastKey orderPaidLastKey, Integer limit) {
        ScanSpec scanSpec = new ScanSpec();

        if (limit != null && limit > 0) {
            scanSpec.withMaxResultSize(limit);
        }else {
            scanSpec.withMaxResultSize(max);
        }

        if (orderPaidLastKey != null) {
            KeyAttribute k1 = new KeyAttribute(Order.PAID_DATE, orderPaidLastKey.getPaidDate());
            KeyAttribute k2 = new KeyAttribute(Order.PAID_TIME, orderPaidLastKey.getPaidTime());
            KeyAttribute k3 = new KeyAttribute(Order.MAIL, orderPaidLastKey.getMail());
            KeyAttribute k4 = new KeyAttribute(Order.ORDER_DATE_TIME, orderPaidLastKey.getOrderDateTime());

            scanSpec.withExclusiveStartKey(k1, k2, k3, k4);
        }

        Index index = orderTable.getIndex(paidIndex);

        ItemCollection<ScanOutcome> collection = index.scan(scanSpec);

        List<Order> orders = mapScanOutcomeToOrders(collection);

        OrderPaidLastKey key = getPaidIndexScanOutcomeLastKey(collection);

        return Orders.of(orders, key);
    }

    @Override
    public Orders getAllProcessingOrders() {
        return getAllProcessingOrders(null, null);
    }

    @Override
    public Orders getAllProcessingOrders(OrderProcessingLastKey orderProcessingLastKey, Integer limit) {
        ScanSpec scanSpec = new ScanSpec();

        if (limit != null && limit > 0) {
            scanSpec.withMaxResultSize(limit);
        }else {
            scanSpec.withMaxResultSize(max);
        }

        if (orderProcessingLastKey != null) {
            KeyAttribute k1 = new KeyAttribute(Order.PROCESSING_DATE, orderProcessingLastKey.getProcessingDate());
            KeyAttribute k2 = new KeyAttribute(Order.OWNER, orderProcessingLastKey.getOwner());
            KeyAttribute k3 = new KeyAttribute(Order.MAIL, orderProcessingLastKey.getMail());
            KeyAttribute k4 = new KeyAttribute(Order.ORDER_DATE_TIME, orderProcessingLastKey.getOrderDateTime());

            scanSpec.withExclusiveStartKey( k1, k2, k3, k4);
        }

        Index index = orderTable.getIndex(processingIndex);

        ItemCollection<ScanOutcome> collection = index.scan(scanSpec);

        List<Order> orders = mapScanOutcomeToOrders(collection);

        OrderProcessingLastKey key = getProcessingIndexScanOutcomeLastKey(collection);

        return Orders.of(orders, key);
    }

    @Override
    public Orders getAllShippedOrders() {

        return getAllShippedOrders(null, null);
    }

    @Override
    public Orders getAllShippedOrders(
            OrderShippedLastKey orderShippedLastKey, Integer limit) {

        ScanSpec scanSpec = new ScanSpec();

        if (limit != null && limit > 0) {
            scanSpec.withMaxResultSize(limit);
        }else {
            scanSpec.withMaxResultSize(max);
        }

        if (orderShippedLastKey != null) {
            KeyAttribute k1 = new KeyAttribute(Order.SHIPPED_DATE, orderShippedLastKey.getShippedDate());
            KeyAttribute k2 = new KeyAttribute(Order.SHIPPED_TIME, orderShippedLastKey.getShippedTime());
            KeyAttribute k3 = new KeyAttribute(Order.MAIL, orderShippedLastKey.getMail());
            KeyAttribute k4 = new KeyAttribute(Order.ORDER_DATE_TIME, orderShippedLastKey.getOrderDateTime());

            scanSpec.withExclusiveStartKey(k1, k2, k3, k4);
        }

        Index index = orderTable.getIndex(shippedIndex);

        ItemCollection<ScanOutcome> collection = index.scan(scanSpec);

        List<Order> orders = mapScanOutcomeToOrders(collection);

        OrderShippedLastKey key = getShippedIndexScanOutcomeLastKey(collection);

        return Orders.of(orders, key);
    }

    @Override
    public Orders getOrdersByMail(String mail) {
        //todo throw exception

        QuerySpec spec = new QuerySpec()
                .withHashKey(Order.MAIL, mail);

        ItemCollection<QueryOutcome> collection = orderTable.query(spec);

        List<Order> orders = mapQueryOutcomeToOrders(collection);
        OrderKey orderKey = getQueryOutcomeLastKey(collection);
        return Orders.of(orders, orderKey);
    }


    @Override
    public Order getOrdersByMailAndTime(String mail, String orderDateTime) {
        //todo throw exception

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
        //todo throw exception

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
        //todo throw exception

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
        //todo throw exception

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
        //todo throw exception

        LocalDateTime now =  LocalDateTime.now();
        String paidDate = now.toLocalDate().toString();
        String paidTime = now.toLocalTime().toString();
        return payOrder(mail, orderDateTime, paidDate, paidTime);
    }

    @Override
    public Order payOrder(String mail, String orderDateTime, String paidDate, String paidTime) {
        //todo throw exception

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
        //todo throw exception
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.ORDER_DATE_TIME, orderDateTime)
                .withConditionExpression(
                                        "attribute_exists(" + Order.IS_ACTIVE +")" +
                                        " and attribute_exists(" + Order.PAID_DATE +")" +
                                        " and attribute_exists(" + Order.PAID_TIME +")" +
                                        " and attribute_exists(" + Order.PROCESSING_DATE + ")" +
                                        " and attribute_not_exists(" + Order.SHIPPED_TIME + ")" +
                                        " and attribute_not_exists(" + Order.SHIPPED_TIME + ")")
                .withUpdateExpression(
                        "remove " + Order.PAID_DATE + "," + Order.PAID_TIME+ "," + Order.PROCESSING_DATE)
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
        //todo throw exception
        LocalDateTime now =  LocalDateTime.now();
        String shippedDate = now.toLocalDate().toString();
        String shippedTime = now.toLocalTime().toString();
        return shipOrder(mail, orderDateTime, shippedDate, shippedTime);
    }

    @Override
    public Order shipOrder(String mail, String orderDateTime, String shippedDate, String shippedTime) {
        //todo throw exception
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
        //todo throw exception
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
    public CashReport getAllCashReport() {

        final List<Order> allOrders = new LinkedList<>();

        OrderPaidLastKey lastKey = null;

        do {
            ScanSpec scanSpec = new ScanSpec();


            if (lastKey != null) {
                KeyAttribute[] keys = getPaidLastKeyAttributes(lastKey);

                scanSpec.withExclusiveStartKey(keys);
            }

            ItemCollection<ScanOutcome> collection =
                    orderTable.getIndex(paidIndex).scan(scanSpec);

            List<Order> orders = mapScanOutcomeToOrders(collection);

            lastKey = getPaidIndexScanOutcomeLastKey(collection);

            allOrders.addAll(orders);
        }while (lastKey != null);

        Double revenue = getRevenue(allOrders);

        return CashReport.of(allOrders, revenue);
    }

    @Override
    public CashReport getDailyCashReport(String paidDate) {
        //todo throw exception
        final List<Order> paidOrders = getDailyPaidOrders(paidDate);

        Double revenue = getRevenue(paidOrders);

        return CashReport.of(paidOrders, revenue);
    }

    @Override
    public CashReport getRangeCashReport(String fromPaidDate, String toPaidDate) {
        //todo throw exception
        LocalDate from = LocalDate.parse(fromPaidDate);
        LocalDate to = LocalDate.parse(toPaidDate);


        if (to.isBefore(from)) {
            return null;
        }

        LocalDate temp = from;

        List<Order> receipts = new LinkedList<>();

        while (!temp.isAfter(to)){
            receipts.addAll(getDailyPaidOrders(temp.toString()));

            temp = temp.plusDays(1);
        }

        Double revenue = getRevenue(receipts);

        return CashReport.of(receipts, revenue);
    }

    private Double getRevenue(List<Order> receipts) {
        return receipts.stream().mapToDouble(Order::getPrice).sum();
    }


    private List<Order> getDailyPaidOrders(String paidDate) {
        final List<Order> allOrders = new LinkedList<>();

        OrderPaidLastKey lastKey = null;

        do {
            QuerySpec querySpec = new QuerySpec()
                    .withHashKey(Order.PAID_DATE, paidDate);

            if (lastKey != null) {
                KeyAttribute[] keys = getPaidLastKeyAttributes(lastKey);
                querySpec.withExclusiveStartKey(keys);
            }

            ItemCollection<QueryOutcome> collection =
                    orderTable.getIndex(paidIndex).query(querySpec);

            List<Order> orders = mapQueryOutcomeToOrders(collection);

            lastKey = getPaidIndexQueryOutcomeLastKey(collection);

            allOrders.addAll(orders);

        }while (lastKey != null);

        return allOrders;
    }

    private KeyAttribute[] getPaidLastKeyAttributes(OrderPaidLastKey lastKey) {
        KeyAttribute[] keys = new KeyAttribute[4];
        keys[0]= new KeyAttribute(Order.MAIL, lastKey.getMail());
        keys[1] = new KeyAttribute(Order.ORDER_DATE_TIME, lastKey.getOrderDateTime());
        keys[2] = new KeyAttribute(Order.PAID_TIME, lastKey.getPaidTime());
        keys[3] = new KeyAttribute(Order.PAID_DATE, lastKey.getPaidDate());
        return keys;
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

    private OrderPaidLastKey getPaidIndexScanOutcomeLastKey(ItemCollection<ScanOutcome> collection) {
        ScanOutcome scanOutcome = collection.getLastLowLevelResult();
        Map<String, AttributeValue> lastKeyEvaluated = scanOutcome.getScanResult().getLastEvaluatedKey();

        return getOrderPaidLastKey(lastKeyEvaluated);
    }

    private OrderPaidLastKey getPaidIndexQueryOutcomeLastKey(ItemCollection<QueryOutcome> collection) {
        QueryOutcome scanOutcome = collection.getLastLowLevelResult();
        Map<String, AttributeValue> lastKeyEvaluated = scanOutcome.getQueryResult().getLastEvaluatedKey();

        return getOrderPaidLastKey(lastKeyEvaluated);
    }

    private OrderPaidLastKey getOrderPaidLastKey(Map<String, AttributeValue> lastKeyEvaluated) {
        OrderPaidLastKey orderPaidLastKey = null;

        if (lastKeyEvaluated != null) {//null if it is last one
            orderPaidLastKey = OrderPaidLastKey.of(
                    lastKeyEvaluated.get(Order.PAID_DATE).getS(),
                    lastKeyEvaluated.get(Order.PAID_TIME).getS(),
                    lastKeyEvaluated.get(Order.MAIL).getS(),
                    lastKeyEvaluated.get(Order.ORDER_DATE_TIME).getS());
        }

        return orderPaidLastKey;
    }

    private OrderProcessingLastKey getProcessingIndexScanOutcomeLastKey(ItemCollection<ScanOutcome> collection) {
        ScanOutcome scanOutcome = collection.getLastLowLevelResult();
        Map<String, AttributeValue> lastKeyEvaluated = scanOutcome.getScanResult().getLastEvaluatedKey();

        OrderProcessingLastKey orderProcessingLastKey = null;

        if (lastKeyEvaluated != null) {//null if it is last one
            orderProcessingLastKey = OrderProcessingLastKey.of(
                    lastKeyEvaluated.get(Order.PROCESSING_DATE).getS(),
                    lastKeyEvaluated.get(Order.OWNER).getS(),
                    lastKeyEvaluated.get(Order.MAIL).getS(),
                    lastKeyEvaluated.get(Order.ORDER_DATE_TIME).getS());
        }

        return orderProcessingLastKey;
    }

    private OrderShippedLastKey getShippedIndexScanOutcomeLastKey(ItemCollection<ScanOutcome> collection) {
        ScanOutcome scanOutcome = collection.getLastLowLevelResult();
        Map<String, AttributeValue> lastKeyEvaluated = scanOutcome.getScanResult().getLastEvaluatedKey();

        OrderShippedLastKey orderShippedLastKey = null;

        if (lastKeyEvaluated != null) {//null if it is last one
            orderShippedLastKey = OrderShippedLastKey.of(
                    lastKeyEvaluated.get(Order.SHIPPED_DATE).getS(),
                    lastKeyEvaluated.get(Order.SHIPPED_TIME).getS(),
                    lastKeyEvaluated.get(Order.MAIL).getS(),
                    lastKeyEvaluated.get(Order.ORDER_DATE_TIME).getS());
        }

        return orderShippedLastKey;
    }


    private List<Order> mapScanOutcomeToOrders(ItemCollection<ScanOutcome> collection) {
        List<Order> orders = new LinkedList<>();

        collection.forEach(
                (s) -> {
                    orders.add(getOrder(s.toJSON()));
                }
        );

        return Collections.unmodifiableList(orders);
    }

    private List<Order> mapQueryOutcomeToOrders(ItemCollection<QueryOutcome> collection) {
        List<Order> orders = new LinkedList<>();

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
