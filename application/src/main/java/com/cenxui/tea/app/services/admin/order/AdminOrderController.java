package com.cenxui.tea.app.services.admin.order;

import com.cenxui.tea.app.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.tea.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderRepository;
import com.cenxui.tea.app.services.CoreController;
import com.cenxui.tea.app.services.util.Param;
import com.cenxui.tea.app.services.util.Path;
import com.cenxui.tea.app.services.util.error.ApplicationError;
import com.cenxui.tea.app.util.JsonUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

public class AdminOrderController extends CoreController{

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
        String time = getTime(map);
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
        String time = getTime(map);

        return JsonUtil.mapToJson(orderRepository.getOrdersByMailAndTime(mail, time));
    };

    public static final Route getAllPaidOrders = (Request request, Response response) -> {
        return JsonUtil.mapToJson(orderRepository.getAllPaidOrders());
    };

    public static final Route getAllPaidOrdersByLastKey = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String paidTime = getPaidTime(map);
        Integer limit = getLimit(map);

        return JsonUtil.mapToJson(orderRepository.getAllPaidOrders(paidTime, limit));
    };

    public static final Route getAllProcessingOrders = (Request request, Response response) -> {
        return JsonUtil.mapToJson(orderRepository.getAllProcessingOrders());
    };

    public static final Route getAllProcessingOrdersByLastKey = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String processingDate = getProcessingDate(map);
        Integer limit = getLimit(map);

        return JsonUtil.mapToJson(orderRepository.getAllProcessingOrders(processingDate, limit));
    };

    public static final Route getAllShippedOrders = (Request request, Response response) -> {
        return JsonUtil.mapToJson(orderRepository.getAllShippedOrders());
    };

    public static final Route getAllShippedOrdersByLastKey = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String shippedTime = getShippedTime(map);
        Integer limit = getLimit(map);

        return JsonUtil.mapToJson(orderRepository.getAllShippedOrders(shippedTime, limit));
    };

    public static final Route activeOrder =  (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getTime(map);
        Order order = orderRepository.activeOrder(mail, time);

        return JsonUtil.mapToJson(order);
    };

    public static final Route deActiveOrder =  (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getTime(map);
        Order order = orderRepository.deActiveOrder(mail, time);

        return JsonUtil.mapToJson(order);
    };

    public static final Route payOrder =  (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getTime(map);
        Order order = orderRepository.payOrder(mail, time);

        return JsonUtil.mapToJson(order);
    };

    public static final Route dePayOrder = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getTime(map);
        Order order = orderRepository.dePayOrder(mail, time);

        return JsonUtil.mapToJson(order);
    };

    public static final Route shipOrder =  (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getTime(map);
        Order order = orderRepository.shipOrder(mail, time);

        return JsonUtil.mapToJson(order);
    };


    public static final Route deShipOrder = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getTime(map);
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

    private static String getTime(Map<String, String> map) {
        return map.get(Param.ORDER_TIME);
    }

    private static Integer getLimit(Map<String, String> map) {
        //todo throw exception
        Integer count = Integer.valueOf(map.get(Param.ORDER_LIMIT));

        return count;
    }

    private static String getPaidTime(Map<String, String> map) {
        return map.get(Param.ORDER_PAID_TIME);
    }

    private static String getProcessingDate(Map<String, String> map) {
        return map.get(Param.ORDER_PROCESSING_DATE);
    }

    private static String getShippedTime(Map<String, String> map) {
        return map.get(Param.ORDER_SHIPPED_TIME);
    }

}
