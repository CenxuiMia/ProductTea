package com.cenxui.tea.app.services.order;

import com.cenxui.tea.app.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.services.CoreController;
import com.cenxui.tea.app.services.Header;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class OrderController extends CoreController {

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
        String body = request.body();

//        String mail = request.headers(Header.MAIL) != null ? request.headers(Header.MAIL) : "example@example.com";

        //todo modify order

        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(body, Order.class);

        System.out.println(order);

        try {
            DynamoDBRepositoryService.getOrderRepository().addOrder(order);
        }catch (Throwable e) {

            StringBuilder trace = new StringBuilder();

            for (StackTraceElement element : e.getStackTrace()) {
                trace.append(element.toString());
            }

            return "data repository error : " + trace.toString();
        }

        return "success";
    };

    public static Route removeOrder = (Request request, Response response) -> {
        throw new UnsupportedOperationException("not yet");
    };

    public static Route removeUser = (Request request, Response response) -> {

        throw new UnsupportedOperationException("not yet");
    };
}

