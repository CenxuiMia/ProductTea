package com.cenxui.shop.web.app.controller.order;

import com.cenxui.shop.repositories.order.OrderRepository;
import com.cenxui.shop.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.shop.repositories.order.attribute.OrderAttribute;
import com.cenxui.shop.repositories.order.attribute.PaymentMethod;
import com.cenxui.shop.web.app.aws.ses.SESMessageService;
import com.cenxui.shop.web.app.config.AWSDynamoDBConfig;
import com.cenxui.shop.repositories.order.Order;
import com.cenxui.shop.repositories.order.attribute.ShippingWay;
import com.cenxui.shop.web.app.config.GoogleReCAPTCHAConfig;
import com.cenxui.shop.web.app.controller.CoreController;
import com.cenxui.shop.web.app.controller.util.Header;
import com.cenxui.shop.web.app.controller.util.Param;
import com.cenxui.shop.util.JsonUtil;
import com.cenxui.shop.util.TimeUtil;
import com.cenxui.shop.web.app.service.SendMessageService;
import com.cenxui.shop.web.app.aws.sns.SNSSendMessageService;
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
                    AWSDynamoDBConfig.PRODUCT_TABLE
            );

    private static final SendMessageService messageService = new SNSSendMessageService();

    private static final SendMessageService mailMessageService = new SESMessageService();

    public static final Route getOrdersByMail = (Request request, Response response) -> {
        String mail = request.headers(Header.MAIL);

        if (mail == null) throw new OrderControllerServerException("header mail cannot be null");

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
        checkRequestBody(body);

        String mail = request.headers(Header.MAIL);
        checkHeaderMail(mail);

        Order order = mapRequestBodyToOrder(body);
        checkOrder(order);

        /**
         * add order
         * mail from header
         * orderDateTime created
         * other item from client
         */

        Order result = orderRepository.addOrder(
                Order.of(
                        mail,
                        TimeUtil.getNowDateTime(),
                        order.getProducts(),
                        order.getPurchaser(),
                        order.getPurchaserPhone(),
                        null,
                        null,
                        null,
                        null,
                        order.getPaymentMethod(),
                        order.getBankInformation(),
                        order.getReceiver(),
                        order.getReceiverPhone(),
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
        /**
         * send order message to end user
         */

        //todo exception
        messageService.sendMessage(result);
        mailMessageService.sendMessage(result);

        return JsonUtil.mapToJsonIgnoreNull(result);
    };

    public static final Route trialOrder = ((request, response) ->  {

        Order order = JsonUtil.mapToOrder(request.body());

        checkTrialOrder(order);

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

    private static void checkOrder(Order order) {

        checkTrialOrder(order);

        if (!OrderAttribute.checkPurchaser(order.getPurchaser()))
            throw new OrderControllerClientException("request body order purchaser cannot be empty");

        if (!OrderAttribute.checkPurchaserPhone(order.getPurchaserPhone()))
            throw new OrderControllerClientException("request body order purchaserPhone cannot be empty");

        if (!OrderAttribute.checkReceiver(order.getReceiver()))
            throw new OrderControllerClientException("request body order receiver cannot be empty");

        if (!OrderAttribute.checkReceiverPhone(order.getReceiverPhone()))
            throw new OrderControllerClientException("request body order receiverPhone cannot be empty");

        if (!OrderAttribute.checkShippingAddress(order.getShippingAddress()))
            throw new OrderControllerClientException("request body order shippingAddress cannot be empty");

        if (!OrderAttribute.checkPaymentMethod(order.getPaymentMethod())) {
            throw new OrderControllerClientException("request body order paymentMethod not allowed");
        }

        if (!OrderAttribute.checkComment(order.getComment())) {
            throw new OrderControllerClientException("request body order comment not allowed");
        }

        if (!OrderAttribute.checkBankInformation(order.getPaymentMethod(), order.getBankInformation())) {
            throw new OrderControllerClientException(
                    "request body order bankInformation with paymentMethod not allowed");
        }
    }

    private static void checkTrialOrder(Order order) {
        if (order == null) throw new OrderControllerClientException("request body order cannot be null");

        if (!OrderAttribute.checkProducts(order.getProducts()))
            throw new OrderControllerClientException("request body order products cannot be empty");

        if (!OrderAttribute.checkShippingWay(order.getShippingWay())){
            throw new OrderControllerClientException("request body order shippedWay not allowed");
        }
    }

    private static void checkHeaderMail(String mail) {
        if (mail == null) throw new OrderControllerServerException("header mail cannot be null.");
        if (mail.length() == 0) throw new OrderControllerServerException("header mail cannot be empty.");
    }

    private static void checkRequestBody(String body) {
        if (body == null || body.isEmpty()) throw new OrderControllerClientException("request body cannot empty.");
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

