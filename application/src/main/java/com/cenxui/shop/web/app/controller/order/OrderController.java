package com.cenxui.shop.web.app.controller.order;

import com.cenxui.shop.repositories.order.OrderRepository;
import com.cenxui.shop.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.shop.util.ApplicationError;
import com.cenxui.shop.web.app.aws.lambda.handlers.AuthLambdaHandler;
import com.cenxui.shop.web.app.config.AWSDynamoDBConfig;
import com.cenxui.shop.repositories.order.Order;
import com.cenxui.shop.repositories.order.ShippedWay;
import com.cenxui.shop.web.app.config.GoogleReCAPTCHAConfig;
import com.cenxui.shop.web.app.controller.CoreController;
import com.cenxui.shop.web.app.controller.util.Header;
import com.cenxui.shop.web.app.controller.util.Param;
import com.cenxui.shop.util.JsonUtil;
import com.cenxui.shop.util.TimeUtil;
import com.cenxui.shop.web.app.service.SendMessageService;
import com.cenxui.shop.web.app.aws.sns.SNSSendMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.JSONTokener;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class OrderController extends CoreController {
    private static final boolean isLocal = System.getenv("Cloud") == null;

    private static final OrderRepository orderRepository =
            DynamoDBRepositoryService.getOrderRepository(
                    AWSDynamoDBConfig.REGION,
                    AWSDynamoDBConfig.ORDER_TABLE,
                    AWSDynamoDBConfig.ORDER_PAID_INDEX,
                    AWSDynamoDBConfig.ORDER_PROCESSING_INDEX,
                    AWSDynamoDBConfig.ORDER_SHIPPED_INDEX,
                    AWSDynamoDBConfig.PRODUCT_TABLE
            );

    private static final SendMessageService sendMessageService = new SNSSendMessageService();

    public static final Route getOrdersByMail = (Request request, Response response) -> {
        String mail = request.headers(Header.MAIL);

        if (mail == null) throw new OrderControllerServerException("header mail can not be null");

        return JsonUtil.mapToJsonIgnoreNull(orderRepository.getOrdersByMail(mail));
    };

    public static final Route addOrder = (Request request, Response response) -> {
        if (!isLocal) {
            String recaptcha = request.queryParams(Header.RECAPTCHA);
            URL url = new URL(GoogleReCAPTCHAConfig.SITE_VERIFY_URL);
            StringBuilder postData = new StringBuilder();
            addParam(postData, GoogleReCAPTCHAConfig.SECRET_PARAM, GoogleReCAPTCHAConfig.SITE_SECRET);
            addParam(postData, GoogleReCAPTCHAConfig.RESPONSE_PARAM, recaptcha);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty(
                    "Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty(
                    "charset", StandardCharsets.UTF_8.displayName());
            urlConnection.setRequestProperty(
                    "Content-Length", Integer.toString(postData.length()));
            urlConnection.setUseCaches(false);
            urlConnection.getOutputStream()
                    .write(postData.toString().getBytes(StandardCharsets.UTF_8));

            JSONTokener jsonTokener = new JSONTokener(urlConnection.getInputStream());

            if (!new JSONObject(jsonTokener).getBoolean("success")) return "you are not allow here";
        }

        String body = request.body();

        if (body == null || body.isEmpty()) throw new OrderControllerClientException("request body cannot empty.");

        String mail = request.headers(Header.MAIL);

        if (mail == null) throw new OrderControllerServerException("header mail can not be null.");

        Order order = mapRequestBodyToOrder(body);

        ableToAddOrder(order);

        Order result = orderRepository.addOrder(
                Order.of(
                        mail,
                        TimeUtil.getNowDateTime(),
                        order.getProducts(),
                        order.getPurchaser(),
                        null,
                        null,
                        null,
                        null,
                        order.getPaymentMethod(),
                        order.getReceiver(),
                        order.getPhone(),
                        order.getShippingWay(),
                        order.getShippingAddress(),
                        order.getComment(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        true,
                        "admin"));

        sendMessageService.sendMessage(result);

        return JsonUtil.mapToJsonIgnoreNull(result);
    };


    public static final Route trialOrder = ((request, response) ->  {

        Order order = JsonUtil.mapToOrder(request.body());

        ableToTrialOrder(order);

        return JsonUtil.mapToJsonIgnoreNull(orderRepository.trialOrder(order));
    });


    /**
     * not allowed user active order
     */
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

    private static void ableToAddOrder(Order order) {

        ableToTrialOrder(order);

        if (isEmpty(order.getPurchaser()))
            throw new OrderControllerClientException("request body order purchaser cannot be empty");

        if (isEmpty(order.getReceiver()))
            throw new OrderControllerClientException("request body order receiver cannot be empty");

        if (isEmpty(order.getPhone()))
            throw new OrderControllerClientException("request body order phone cannot be empty");

        if (isEmpty(order.getShippingAddress()))
            throw new OrderControllerClientException("request body order shippingAddress cannot be empty");
    }

    private static void ableToTrialOrder(Order order) {
        if (order == null) throw new OrderControllerClientException("request body order cannot be null");

        if (order.getProducts() == null || order.getProducts().isEmpty())
            throw new OrderControllerClientException("request body order products cannot be empty");

        if (!ShippedWay.SHOP.equals(order.getShippingWay()) &&
                !ShippedWay.HOME.equals(order.getShippingWay())) {
            throw new OrderControllerClientException("request body order shippedWay not allowed");
        }
    }

    private static boolean isEmpty(String s) {
        if (s == null || s.isEmpty()) return true;
        return false;
    }

    private static String getOrderDateTime(Map<String, String> map) {
        return map.get(Param.ORDER_DATE_TIME);
    }

    private static StringBuilder addParam(
            StringBuilder postData, String param, String value)
            throws UnsupportedEncodingException {
        if (postData.length() != 0) {
            postData.append("&");
        }
        return postData.append(
                String.format("%s=%s",
                        URLEncoder.encode(param, StandardCharsets.UTF_8.displayName()),
                        URLEncoder.encode(value, StandardCharsets.UTF_8.displayName())));
    }

}

