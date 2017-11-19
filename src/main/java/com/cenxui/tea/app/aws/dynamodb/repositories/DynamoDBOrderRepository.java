package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.cenxui.tea.app.repositories.order.OrderKey;
import com.cenxui.tea.app.aws.dynamodb.exceptions.order.OrderProductFormatException;
import com.cenxui.tea.app.aws.dynamodb.util.ItemUtil;
import com.cenxui.tea.app.aws.dynamodb.util.exception.DuplicateProductException;
import com.cenxui.tea.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderRepository;
import com.cenxui.tea.app.repositories.order.OrderResult;
import com.cenxui.tea.app.util.JsonUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * todo scan should have some limit
 */

class DynamoDBOrderRepository implements OrderRepository<OrderKey> {

    private final Table orderTable = DynamoDBManager.getDynamoDB().getTable(DynamoDBConfig.ORDER_TABLE);

    @Override
    public OrderResult getAllOrder(Integer limit, String mail, String time) {
        ScanSpec scanSpec = new ScanSpec()
                .withMaxResultSize(limit)
                .withExclusiveStartKey(Order.MAIL, mail, Order.TIME, time);

        ItemCollection collection = orderTable.scan(scanSpec);
        List<Order> orders = mapToOrders(collection);
        OrderKey orderKey = getLastKey(collection);
        return OrderResult.of(orders, orderKey);
    }

    @Override
    public OrderResult getAllProcessingOrders(Integer limit, String mail, String time) {
        return scanIndex(limit, mail, time, DynamoDBConfig.ORDER_PROCESSING_INDEX);
    }

    @Override
    public OrderResult getAllShippedOrders(Integer limit, String mail, String time) {
        return scanIndex(limit, mail, time, DynamoDBConfig.ORDER_SHIPPED_INDEX);
    }

    @Override
    public OrderResult getAllPaidOrders(Integer limit, String mail, String time) {
        return scanIndex(limit, mail, time, DynamoDBConfig.ORDER_PAID_INDEX);
    }

    @Override
    public OrderResult getOrderByMail(String mail) {
        throw new UnsupportedOperationException("not yet");
        //todo
    }

    @Override
    public OrderResult getAllOrders() {
        ItemCollection collection = orderTable.scan();
        List<Order> orders = mapToOrders(collection);
        OrderKey orderKey = getLastKey(collection);

        return OrderResult.of(orders, orderKey);
    }

    @Override
    public OrderResult getAllProcessingOrders() {
        return scanIndex(DynamoDBConfig.ORDER_PROCESSING_INDEX);
    }


    @Override
    public OrderResult getAllShippedOrders() {
        return scanIndex(DynamoDBConfig.ORDER_SHIPPED_INDEX);
    }

    @Override
    public OrderResult getAllPaidOrders() {
        return scanIndex(DynamoDBConfig.ORDER_PAID_INDEX);
    }


    @Override
    public Order getOrdersByMailAndTime(String mail, String time) {
        throw new UnsupportedOperationException("not yet");
        //todo
    }


    @Override
    public Order addOrder(String mail, Order clientOrder) {
        Float money = 0F;

        List<String> products = clientOrder.getProducts();

        for (String product: products) {
            String[] s = product.split(";");

            if (s.length != 3) throw new OrderProductFormatException(product);

            Float price = DynamoDBRepositoryService.getProductRepository().getProductPrice(s[0].trim(), s[1].trim());

            money = money + price * Float.valueOf(s[2].trim());
        }



        Order order = Order.of(
                mail,
                clientOrder.getProducts(),      //todo modify products
                clientOrder.getPurchaser(),
                money,         //todo modify order money
                clientOrder.getReceiver(),
                clientOrder.getPhone(),
                clientOrder.getAddress(),
                clientOrder.getComment(),
                null,
                null,
                null,
                Boolean.TRUE);
        try {
            PutItemSpec putItemSpec = new PutItemSpec()
                    .withItem(ItemUtil.getOrderItem(order))
                    .withConditionExpression("attribute_not_exists("+ Order.MAIL + ")");
            orderTable.putItem(putItemSpec);
        } catch (DuplicateProductException e) {
            System.out.println("Product record can not be duplicated "); //todo modify to runtime exception
        } catch (ConditionalCheckFailedException e) {
            System.out.println("Record already exists in Dynamo DB Table"); //todo modify to runtime exception
        }
        return order;
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
                .withConditionExpression("attribute_not_exists" + Order.IS_ACTIVE +")")
                .withUpdateExpression("set " + Order.IS_ACTIVE + "=:ia")
                .withValueMap(new ValueMap().withBoolean( ":ia", true))
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String orderJson = outcome.getItem().toJSON();

        return  JsonUtil.mapToOrder(orderJson);
    }

    @Override
    public Order deActiveOrder(String mail, String time) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.TIME, time)
                .withConditionExpression(
                        "attribute_not_exists(" + Order.PAID_DATE + ")" +
                        " and attribute_exists(" + Order.IS_ACTIVE + ")")
                .withUpdateExpression("remove " + Order.IS_ACTIVE)
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String orderJson = outcome.getItem().toJSON();

        return JsonUtil.mapToOrder(orderJson);
    }

    @Override
    public Order payOrder(String mail, String time) {
        String date = LocalDate.now().toString();

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.TIME, time)
                .withUpdateExpression("set " + Order.PAID_DATE + "=:pa,"+ Order.PROCESS_DATE+ "=:pr")
                .withConditionExpression(
                        "attribute_exists(" + Order.IS_ACTIVE +")" +
                        "add attribute_not_exists(" + Order.PAID_DATE + ")")
                .withValueMap(new ValueMap().withString(":pa" , date).withString(":pr", date))
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String orderJson = outcome.getItem().toJSON();

        return JsonUtil.mapToOrder(orderJson);
    }

    @Override
    public Order dePayOrder(String mail, String time) {

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.TIME, time)
                .withConditionExpression("attribute_exists(" + Order.PAID_DATE +")")
                .withUpdateExpression("remove " + Order.PAID_DATE+ "," + Order.PROCESS_DATE)
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String orderJson = outcome.getItem().toJSON();

        return JsonUtil.mapToOrder(orderJson);
    }

    @Override
    public Order shipOrder(String mail, String time) {
        String date = LocalDate.now().toString();

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withConditionExpression(
                        "attribute_exists(" + Order.PAID_DATE +")" +
                        "add attribute_not_exists(" + Order.SHIP_DATE)
                .withPrimaryKey(Order.MAIL, mail, Order.TIME, time)
                .withUpdateExpression("set " + Order.SHIP_DATE+ "=:sh" + " remove " + Order.PROCESS_DATE)
                .withValueMap(new ValueMap().withString(":sh", date))
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String orderJson = outcome.getItem().toJSON();

        return JsonUtil.mapToOrder(orderJson);
    }

    @Override
    public Order deShipOrder(String mail, String time) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(Order.MAIL, mail, Order.TIME, time)
                .withConditionExpression("attribute_exists(" + Order.SHIP_DATE + ")")
                .withUpdateExpression("set " + Order.PROCESS_DATE+ "=" + Order.PAID_DATE +
                        " remove " + Order.SHIP_DATE)
                .withReturnValues(ReturnValue.ALL_NEW);

        UpdateItemOutcome outcome = orderTable.updateItem(updateItemSpec);
        String orderJson = outcome.getItem().toJSON();
        Order order = JsonUtil.mapToOrder(orderJson);

        return order;
    }

    private OrderKey getLastKey(ItemCollection<ScanOutcome> collection) {
        ScanOutcome scanOutcome = collection.getLastLowLevelResult();
        Map<String, AttributeValue> lastKeyEvaluated = scanOutcome.getScanResult().getLastEvaluatedKey();

        return OrderKey.of(
                lastKeyEvaluated.get(Order.MAIL).getS(), lastKeyEvaluated.get(Order.TIME).getS());
    }

    private List<Order> mapToOrders(ItemCollection<Item> collection) {
        List<Order> orders = new ArrayList<>();

        collection.forEach(
                (s) -> {
                    Order order = JsonUtil.mapToOrder(s.toJSON());
                    orders.add(order);
                }
        );

        return Collections.unmodifiableList(orders);
    }

    private OrderResult scanIndex(String indexName) {
        Index index = orderTable.getIndex(indexName);
        ItemCollection collection = index.scan();
        List<Order> orders = mapToOrders(collection);
        OrderKey orderKey = getLastKey(collection);

        return OrderResult.of(orders, orderKey);
    }


    private OrderResult scanIndex(Integer limit, String mail, String time, String indexName) {
        Index index = orderTable.getIndex(indexName);
        ScanSpec scanSpec = new ScanSpec()
                .withMaxResultSize(limit)
                .withExclusiveStartKey(Order.MAIL, mail, Order.TIME, time);
        ItemCollection collection = index.scan(scanSpec);
        List<Order> orders = mapToOrders(collection);
        OrderKey orderKey = getLastKey(collection);
        return OrderResult.of(orders, orderKey);
    }

}
