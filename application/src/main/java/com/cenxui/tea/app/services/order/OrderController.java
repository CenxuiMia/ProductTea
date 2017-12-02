package com.cenxui.tea.app.services.order;

import com.cenxui.tea.app.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.tea.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderRepository;
import com.cenxui.tea.app.services.CoreController;
import com.cenxui.tea.app.services.util.Header;
import com.cenxui.tea.app.services.util.Param;
import com.cenxui.tea.app.services.util.error.ApplicationError;
import com.cenxui.tea.app.util.JsonUtil;
import com.sun.org.apache.xpath.internal.operations.Or;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

public class OrderController extends CoreController {
    private static final OrderRepository orderRepository =
            DynamoDBRepositoryService.getOrderRepository(
                    DynamoDBConfig.REGION,
                    DynamoDBConfig.ORDER_TABLE,
                    DynamoDBConfig.ORDER_PAID_INDEX,
                    DynamoDBConfig.ORDER_PROCESSING_INDEX,
                    DynamoDBConfig.ORDER_SHIPPED_INDEX,
                    DynamoDBConfig.PRODUCT_TABLE
            );

    public static final Route getOrdersByMail = (Request request, Response response) -> {

        String mail = request.headers(Header.MAIL);
        return JsonUtil.mapToJson(orderRepository.getOrdersByMail(mail));
    };

    public static final Route addOrder = (Request request, Response response) -> {

        String body = request.body();

        if (body == null || body.isEmpty()) return "fail";

        String mail = request.headers(Header.MAIL) != null ? request.headers(Header.MAIL) : "example@example.com";

        Order clientOrder = mapRequestBodyToOrder(body);

        if (isValidate(clientOrder) == false) return "fail";

        Order order = Order.of(
                mail,
                clientOrder.getProducts(),
                clientOrder.getPurchaser(),
                null,
                null,
                clientOrder.getReceiver(),
                clientOrder.getPhone(),
                clientOrder.getShippingWay(),
                clientOrder.getShippingAddress(),
                clientOrder.getComment(),
                null,
                null,
                null,
                true);

        Order resultOrder = orderRepository.addOrder(order);

        return JsonUtil.mapToJson(resultOrder);
    };

    public static final Route activeOrder =  (Request request, Response response) -> {
        //todo
        throw new UnsupportedOperationException("not yet");
    };


    public static final Route deActiveOrder =  (Request request, Response response) -> {
        //todo
        throw new UnsupportedOperationException("not yet");
    };

    private static Order mapRequestBodyToOrder(String body) {
        try {
            return JsonUtil.mapToOrder(body);
        }catch (Throwable e) {
            throw new OrderControllerException("request body not allow :" + body);
        }
    }

    private static boolean isValidate(Order order) {

        if (order == null) return false;

        if (order.getProducts() == null || order.getProducts().isEmpty()) return false;

        if (isEmpty(order.getPurchaser())) return false;

        if (isEmpty(order.getReceiver())) return false;

        if (isEmpty(order.getPhone())) return false;

        if (isEmpty(order.getShippingWay())) return false;

        if (isEmpty(order.getShippingAddress())) return false;

        return true;
    }

    private static boolean isEmpty(String s) {
        if (s == null || s.isEmpty()) return true;
        return false;
    }

}

