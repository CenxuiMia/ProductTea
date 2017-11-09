package com.cenxui.tea.app.services.order;

import com.cenxui.tea.app.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.services.CoreController;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

public class OrderController extends CoreController {

    public static Route getAllProducts = (Request request, Response response) -> {
        return DynamoDBRepositoryService.getOrderRepository().getAllOrders();
    };

    public static Route getOrderByTMail = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = map.get(Order.MAIL);
        return DynamoDBRepositoryService.getOrderRepository().getOrderByTMail(mail);
    };

    public static Route getOrdersByMailAndTime = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = map.get(Order.MAIL);
        String time = map.get(Order.TIME);

        return DynamoDBRepositoryService.getOrderRepository().getOrdersByMailAndTime(mail, time);
    };

    public static Route addOrder = (Request request, Response response) -> {

        throw new UnsupportedOperationException("not yet");
    };

    public static Route removeOrder = (Request request, Response response) -> {
        throw new UnsupportedOperationException("not yet");
    };

    public static Route removeUser = (Request request, Response response) -> {

        throw new UnsupportedOperationException("not yet");
    };
}

