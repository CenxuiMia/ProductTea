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

        String mail = request.headers(Header.MAIL) != null ? request.headers(Header.MAIL) : "example@example.com";


        ObjectMapper mapper = new ObjectMapper();
        Order clientOrder = mapper.readValue(body, Order.class);

        Order order = Order.of(
                mail,
                clientOrder.getProducts(),      //todo modify products
                clientOrder.getPurchaser(),
                clientOrder.getMoney(),         //todo modify order money
                clientOrder.getReceiver(),
                clientOrder.getPhone(),
                clientOrder.getAddress(),
                clientOrder.getComment(),
                null,
                null,
                null,
                Boolean.TRUE);

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

    public static final Route removeOrder = (Request request, Response response) -> {
        throw new UnsupportedOperationException("not yet");
    };

    public static final Route payOrder =  (Request request, Response response) -> {
        throw new UnsupportedOperationException("not yet");
    };

    public static Route shipOrder =  (Request request, Response response) -> {
        throw new UnsupportedOperationException("not yet");
    };
}

