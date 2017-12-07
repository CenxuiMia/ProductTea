package com.cenxui.tea.app.services.order;

import com.cenxui.tea.app.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.tea.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderRepository;
import com.cenxui.tea.app.services.CoreController;
import com.cenxui.tea.app.services.util.Header;
import com.cenxui.tea.app.services.util.Param;
import com.cenxui.tea.app.util.JsonUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.LocalDateTime;
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

        if (mail == null) throw new OrderControllerServerException("header mail can not be null");

        return JsonUtil.mapToJsonIgnoreNull(orderRepository.getOrdersByMail(mail));
    };

    public static final Route addOrder = (Request request, Response response) -> {

        String body = request.body();

        if (body == null || body.isEmpty()) throw new OrderControllerClientException("request body cannot empty.");

        String mail = request.headers(Header.MAIL);

        if (mail == null) throw new OrderControllerServerException("header mail can not be null.");

        Order clientOrder = mapRequestBodyToOrder(body);

        if (!isValidate(clientOrder)) throw new OrderControllerClientException("request body is not acceptable.");

        Order order = Order.of(
                mail,
                LocalDateTime.now().toString().substring(0,19),
                clientOrder.getProducts(),
                clientOrder.getPurchaser(),
                null,
                clientOrder.getPaymentMethod(),
                clientOrder.getReceiver(),
                clientOrder.getPhone(),
                clientOrder.getShippingWay(),
                clientOrder.getShippingAddress(),
                clientOrder.getComment(),
                null,
                null,
                null,
                null,
                null,
                true,
                "admin");

        Order resultOrder = orderRepository.addOrder(order);

        return JsonUtil.mapToJsonIgnoreNull(resultOrder);
    };

    public static final Route activeOrder =  (Request request, Response response) -> {
        String mail = request.headers(Header.MAIL);
        String orderDateTime = getOrderDateTime(request.params());
        return JsonUtil.mapToJsonIgnoreNull(orderRepository.activeOrder(mail, orderDateTime));
    };


    public static final Route deActiveOrder =  (Request request, Response response) -> {
        String mail = request.headers(Header.MAIL);
        String orderDateTime = getOrderDateTime(request.params());
        return JsonUtil.mapToJsonIgnoreNull(orderRepository.deActiveOrder(mail, orderDateTime));
    };

    private static Order mapRequestBodyToOrder(String body) {
        try {
            return JsonUtil.mapToOrder(body);
        }catch (Throwable e) {
            throw new OrderControllerServerException("request body not allow :" + body);
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

    private static String getOrderDateTime(Map<String, String> map) {
        return map.get(Param.ORDER_DATE_TIME);
    }

}

