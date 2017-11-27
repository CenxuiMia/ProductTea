package com.cenxui.tea.app.services.admin.order;

import com.cenxui.tea.app.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.tea.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderRepository;
import com.cenxui.tea.app.services.CoreController;
import com.cenxui.tea.app.services.util.Param;
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

    public static final Route getOrdersByLastKey = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getTime(map);
        Integer limit = getLimit(map);

        return JsonUtil.mapToJson(orderRepository.getAllOrdersByLastKey(limit, mail, time));
    };

    public static final Route getOrdersByMail = (Request request, Response response) -> {
        try {
            Map<String, String> map = request.params();
            String mail = getMail(map);
            return JsonUtil.mapToJson(orderRepository.getOrdersByMail(mail));
        }catch (Throwable e) {
            return ApplicationError.getTrace(e.getStackTrace());
        }
    };

    public static final Route getOrderByMailAndTime = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getTime(map);

        return JsonUtil.mapToJson(orderRepository.getOrdersByMailAndTime(mail, time));
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

    public static Route dePayOrder = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getTime(map);
        Order order = orderRepository.dePayOrder(mail, time);

        return JsonUtil.mapToJson(order);
    };

    public static Route shipOrder =  (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getTime(map);
        Order order = orderRepository.shipOrder(mail, time);

        return JsonUtil.mapToJson(order);
    };


    public static Route deShipOrder = (Request request, Response response) -> {
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
        Integer count = Integer.valueOf(map.get(Param.ORDER_COUNT));

        return count;
    }

}
