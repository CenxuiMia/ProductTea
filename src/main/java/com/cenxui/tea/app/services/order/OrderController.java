package com.cenxui.tea.app.services.order;

import com.cenxui.tea.app.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.services.CoreController;
import com.cenxui.tea.app.services.Header;
import com.cenxui.tea.app.util.JsonUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

public class OrderController extends CoreController {

    public static final Route getOrderByTMail = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = map.get(Order.MAIL);
        return DynamoDBRepositoryService.getOrderRepository().getOrderByTMail(mail);
    };

    public static final Route getOrdersByMailAndTime = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = map.get(Order.MAIL);
        String time = map.get(Order.TIME);

        return DynamoDBRepositoryService.getOrderRepository().getOrdersByMailAndTime(mail, time);
    };

    public static final Route addOrder = (Request request, Response response) -> {
        String body = request.body();

        if (body == null || body.isEmpty()) return "fail";

        String mail = request.headers(Header.MAIL) != null ? request.headers(Header.MAIL) : "example@example.com";

        Order clientOrder = JsonUtil.mapToOrder(body);

        if (isValidate(clientOrder) == false) return "fail";

        try {
            DynamoDBRepositoryService.getOrderRepository().addOrder(mail, clientOrder);
        }catch (Throwable e) {

            StringBuilder trace = new StringBuilder();

            for (StackTraceElement element : e.getStackTrace()) {
                trace.append(element.toString());
            }

            return "data repository error : " + trace.toString();
        }

        return "success";
    };

    public static final Route removeOrder = (Request request, Response response) -> {
        //todo
        throw new UnsupportedOperationException("not yet");
    };

    public static final Route activeOrder =  (Request request, Response response) -> {
        //todo
        throw new UnsupportedOperationException("not yet");
    };

    public static final Route deActiveOrder =  (Request request, Response response) -> {
        //todo
        throw new UnsupportedOperationException("not yet");
    };

    public static final Route payOrder =  (Request request, Response response) -> {
        //todo
        throw new UnsupportedOperationException("not yet");
    };

    public static Route dePayOrder = (Request request, Response response) -> {
        //todo
        throw new UnsupportedOperationException("not yet");
    };

    public static Route shipOrder =  (Request request, Response response) -> {
        //todo
        throw new UnsupportedOperationException("not yet");
    };


    public static Route deShipOrder = (Request request, Response response) -> {
        //todo
        throw new UnsupportedOperationException("not yet");
    };

    public static boolean isValidate(Order order) {

        if (order == null) return false;

        if (order.getProducts() == null || order.getProducts().isEmpty()) return false;

        if (isEmpty(order.getPurchaser())) return false;

        if (isEmpty(order.getReceiver())) return false;

        if (isEmpty(order.getPhone())) return false;

        if (isEmpty(order.getAddress())) return false;

        return true;
    }

    private static boolean isEmpty(String s) {
        if (s == null || s.isEmpty()) return true;
        return false;
    }
}

