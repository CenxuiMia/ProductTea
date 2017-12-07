package com.cenxui.tea.admin.app.service.order;

import com.cenxui.tea.admin.app.service.AdminCoreController;
import com.cenxui.tea.admin.app.util.Param;
import com.cenxui.tea.app.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.tea.admin.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.order.*;
import com.cenxui.tea.app.util.JsonUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

public class AdminOrderController extends AdminCoreController {

    private static final OrderRepository orderRepository =
            DynamoDBRepositoryService.getOrderRepository(
                    DynamoDBConfig.REGION,
                    DynamoDBConfig.ORDER_TABLE,
                    DynamoDBConfig.ORDER_PAID_INDEX,
                    DynamoDBConfig.ORDER_PROCESSING_INDEX,
                    DynamoDBConfig.ORDER_SHIPPED_INDEX,
                    DynamoDBConfig.PRODUCT_TABLE
            );

    public static final Route addOrder = (Request request, Response response) -> {

        String body = request.body();

        Order order = mapRequestBodyToOrder(body);

        Order resultOrder = orderRepository.addOrder(order);

        return JsonUtil.mapToJson(resultOrder);
    };


    public static final Route getAllOrders = (Request request, Response response) -> {
        return JsonUtil.mapToJson(orderRepository.getAllOrders());
    };

    public static final Route getAllOrdersByLastKey = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);
        Integer limit = getLimit(map);

        return JsonUtil.mapToJson(orderRepository.getAllOrders(mail, time, limit));
    };

    public static final Route getOrdersByMail = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        return JsonUtil.mapToJson(orderRepository.getOrdersByMail(mail));
    };

    public static final Route getOrderByMailAndTime = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);

        return JsonUtil.mapToJson(orderRepository.getOrdersByMailAndTime(mail, time));
    };

    public static final Route getAllActiveOrders =  (Request request, Response response) -> {
        return JsonUtil.mapToJson(orderRepository.getAllActiveOrders());
    };

    public static final Route getAllActiveOrdersByLastKey = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);
        Integer limit = getLimit(map);

        return JsonUtil.mapToJson(orderRepository.getAllActiveOrders(mail, time, limit));
    };

    public static final Route getAllPaidOrders = (Request request, Response response) -> {
        return JsonUtil.mapToJson(orderRepository.getAllPaidOrders());
    };

    public static final Route getAllPaidOrdersByLastKey = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String paidDate = getPaidDate(map);
        String paidTime = getPaidTime(map);
        String mail = getMail(map);
        String orderDateTime = getOrderDateTime(map);
        Integer limit = getLimit(map);

        return JsonUtil.mapToJson(orderRepository.getAllPaidOrders(
                OrderPaidLastKey.of(paidDate, paidTime, mail, orderDateTime), limit));
    };

    public static final Route getAllProcessingOrders = (Request request, Response response) -> {
        return JsonUtil.mapToJson(orderRepository.getAllProcessingOrders());
    };

    public static final Route getAllProcessingOrdersByLastKey = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String processingDate = getProcessingDate(map);
        String owner = getOwner(map);
        String mail = getMail(map);
        String orderDateTime = getOrderDateTime(map);
        Integer limit = getLimit(map);

        return JsonUtil.mapToJson(orderRepository.getAllProcessingOrders(
                OrderProcessingLastKey.of(processingDate, owner, mail, orderDateTime), limit));
    };

    public static final Route getAllShippedOrders = (Request request, Response response) -> {
        return JsonUtil.mapToJson(orderRepository.getAllShippedOrders());
    };

    public static final Route getAllShippedOrdersByLastKey = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String shippedDate = getShippedDate(map);
        String shippedTime = getShippedTime(map);
        String mail = getMail(map);
        String orderDateTime = getOrderDateTime(map);

        Integer limit = getLimit(map);

        return JsonUtil.mapToJson(orderRepository.getAllShippedOrders(
                OrderShippedLastKey.of(shippedDate, shippedTime, mail, orderDateTime), limit));
    };

    public static final Route activeOrder =  (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);
        Order order = orderRepository.activeOrder(mail, time);

        return JsonUtil.mapToJson(order);
    };

    public static final Route deActiveOrder =  (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);
        Order order = orderRepository.deActiveOrder(mail, time);

        return JsonUtil.mapToJson(order);
    };

    public static final Route payOrder =  (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);
        Order order = orderRepository.payOrder(mail, time);

        return JsonUtil.mapToJson(order);
    };

    public static final Route dePayOrder = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);
        Order order = orderRepository.dePayOrder(mail, time);

        return JsonUtil.mapToJson(order);
    };

    public static final Route shipOrder =  (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);
        Order order = orderRepository.shipOrder(mail, time);

        return JsonUtil.mapToJson(order);
    };


    public static final Route deShipOrder = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);
        Order order = orderRepository.deShipOrder(mail, time);

        return JsonUtil.mapToJson(order);
    };

    private static Order mapRequestBodyToOrder(String body) {
        try {
            return JsonUtil.mapToOrder(body);
        }catch (Throwable e) {
            throw new AdminOrderControllerException("request body not allow :" + body);
        }
    }

    private static String getMail(Map<String, String> map) {
        return map.get(Param.ORDER_MAIL);
    }

    private static String getOrderDateTime(Map<String, String> map) {
        return map.get(Param.ORDER_DATE_TIME);
    }

    private static Integer getLimit(Map<String, String> map) {
        //todo throw client error exception
        Integer count = Integer.valueOf(map.get(Param.ORDER_LIMIT));

        return count;
    }

    private static String getPaidDate(Map<String, String> map) {
        return map.get(Param.ORDER_PAID_DATE);
    }

    private static String getPaidTime(Map<String, String> map) {
        return map.get(Param.ORDER_PAID_TIME);
    }

    private static String getProcessingDate(Map<String, String> map) {
        return map.get(Param.ORDER_PROCESSING_DATE);
    }

    private static String getOwner(Map<String, String> map) {return map.get(Param.ORDER_OWNER);}


    private static String getShippedDate(Map<String, String> map) {
        return map.get(Param.ORDER_SHIPPED_DATE);
    }

    private static String getShippedTime(Map<String, String> map) {
        return map.get(Param.ORDER_SHIPPED_TIME);
    }

}