package com.cenxui.shop.admin.app.controller.order;

import com.cenxui.shop.admin.app.aws.ses.SESMessageService;
import com.cenxui.shop.admin.app.config.AWSDynamoDBConfig;
import com.cenxui.shop.admin.app.controller.AdminCoreController;
import com.cenxui.shop.admin.app.service.MessageService;
import com.cenxui.shop.admin.app.util.Param;
import com.cenxui.shop.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.shop.repositories.order.*;
import com.cenxui.shop.util.JsonUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.util.Map;

public class AdminOrderController extends AdminCoreController {

    private static final OrderRepository orderRepository =
            DynamoDBRepositoryService.getOrderRepository(
                    AWSDynamoDBConfig.REGION,
                    AWSDynamoDBConfig.ORDER_TABLE,
                    AWSDynamoDBConfig.PRODUCT_TABLE,
                    AWSDynamoDBConfig.COUPON_TABLE
            );

    private static final MessageService sesService = new SESMessageService();

    public static final Route addOrder = (request, response) -> {
        String body = request.body();
        Order order = mapRequestBodyToOrder(body);
        Order resultOrder = orderRepository.addOrder(order);

        return JsonUtil.mapToJson(resultOrder);
    };


    public static final Route getAllOrders = (request, response) -> {
        return JsonUtil.mapToJson(orderRepository.getAllOrders());
    };

    public static final Route getAllOrdersByLastKey = (request, response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);
        Integer limit = getLimit(map);

        return JsonUtil.mapToJson(orderRepository.getAllOrders(mail, time, limit));
    };

    public static final Route getAllActiveOrders =  (request, response) -> {
        return JsonUtil.mapToJson(orderRepository.getAllActiveOrders());
    };

    public static final Route getAllActiveOrdersByLastKey = (request, response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);
        Integer limit = getLimit(map);

        return JsonUtil.mapToJson(orderRepository.getAllActiveOrders(mail, time, limit));
    };

    public static final Route getAllBankOrders = ((request, response) -> {
        return JsonUtil.mapToJson(orderRepository.getAllBankOrders());
    });

    public static final Route getAllBankOrdersByLastKey = ((request, response) -> {
        Map<String, String> map = request.params();
        String bankInformation = getBankInformation(map);
        String mail = getMail(map);
        String orderDateTime = getOrderDateTime(map);
        Integer limit = getLimit(map);

        return JsonUtil.mapToJson(orderRepository.getAllBankOrders(
                OrderBankLastKey.of(bankInformation, mail, orderDateTime), limit));

    });

    public static final Route getAllPaidOrders = (request, response) -> {
        return JsonUtil.mapToJson(orderRepository.getAllPaidOrders());
    };

    public static final Route getAllPaidOrdersByLastKey = (request, response) -> {
        Map<String, String> map = request.params();
        String paidDate = getPaidDate(map);
        String paidTime = getPaidTime(map);
        String mail = getMail(map);
        String orderDateTime = getOrderDateTime(map);
        Integer limit = getLimit(map);

        return JsonUtil.mapToJson(orderRepository.getAllPaidOrders(
                OrderPaidLastKey.of(paidDate, paidTime, mail, orderDateTime), limit));
    };

    public static final Route getAllProcessingOrders = (request, response) -> {
        return JsonUtil.mapToJson(orderRepository.getAllProcessingOrders());
    };

    public static final Route getAllProcessingOrdersByLastKey = (request, response) -> {
        Map<String, String> map = request.params();
        String processingDate = getProcessingDate(map);
        String owner = getOwner(map);
        String mail = getMail(map);
        String orderDateTime = getOrderDateTime(map);
        Integer limit = getLimit(map);

        return JsonUtil.mapToJson(orderRepository.getAllProcessingOrders(
                OrderProcessingLastKey.of(processingDate, owner, mail, orderDateTime), limit));
    };

    public static final Route getAllShippedOrders = (request, response) -> {
        return JsonUtil.mapToJson(orderRepository.getAllShippedOrders());
    };

    public static final Route getAllShippedOrdersByLastKey = (request, response) -> {
        Map<String, String> map = request.params();
        String shippedDate = getShippedDate(map);
        String shippedTime = getShippedTime(map);
        String mail = getMail(map);
        String orderDateTime = getOrderDateTime(map);

        Integer limit = getLimit(map);

        return JsonUtil.mapToJson(orderRepository.getAllShippedOrders(
                OrderShippedLastKey.of(shippedDate, shippedTime, mail, orderDateTime), limit));
    };

    public static final Route getOrdersByMail = (request, response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        return JsonUtil.mapToJson(orderRepository.getOrdersByMail(mail));
    };

    public static final Route getOrdersByMailAndTime = (request, response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);

        return JsonUtil.mapToJson(orderRepository.getOrdersByMailAndTime(mail, time));
    };

    public static final Route getOrdersByBankInformation = ((request, response) -> {
        Map<String, String> map = request.params();
        String bankInformation = getBankInformation(map);

        return JsonUtil.mapToJson(orderRepository.getOrdersByBankInformation(bankInformation));
    });

    public static final Route getOrdersByPaidDate = ((request, response) -> {

        Map<String, String> map = request.params();
        String paidDate = getPaidDate(map);

        return JsonUtil.mapToJson(orderRepository.getOrdersByPaidDate(paidDate));
    });

    public static final Route getOrdersByPaidDateAndPaidTime = ((request, response) -> {

        Map<String, String> map = request.params();
        String paidDate = getPaidDate(map);
        String paidTime = getPaidTime(map);

        return JsonUtil.mapToJson(orderRepository.getOrdersByPaidDateAndPaidTime(paidDate, paidTime));
    });


    public static final Route getOrderByProcessingDate = ((request, response) -> {

        Map<String, String> map = request.params();
        String processingDate = getProcessingDate(map);

        return JsonUtil.mapToJson(orderRepository.getOrdersByProcessingDate(processingDate));
    });

    public static final Route getOrderByProcessingDateAndOwner = ((request, response) -> {

        Map<String, String> map = request.params();
        String processingDate = getProcessingDate(map);
        String owner = getOwner(map);

        return JsonUtil.mapToJson(orderRepository.getOrdersByProcessingDateAndOwner(processingDate, owner));
    });

    public static final Route getOrderByShippedDate = ((request, response) -> {

        Map<String, String> map = request.params();
        String shippedDate = getShippedDate(map);

        return JsonUtil.mapToJson(orderRepository.getOrdersByShippedDate(shippedDate));
    });


    public static final Route getOrderByShippedDateAndShippedTime = ((request, response) -> {

        Map<String, String> map = request.params();
        String shippedDate = getShippedDate(map);
        String shippedTime = getShippedTime(map);

        return JsonUtil.mapToJson(orderRepository.getOrdersByShippedDateAndTime(shippedDate, shippedTime));
    });


    public static final Route activeOrder =  (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);
        Order order = orderRepository.activeOrder(mail, time);

        return JsonUtil.mapToJson(order);
    };

    public static final Route deActiveOrder =  (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);
        Order order = orderRepository.deActiveOrder(mail, time);

        return JsonUtil.mapToJson(order);
    };

    public static final Route payOrder =  (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);
        Order order = orderRepository.payOrder(mail, time);

        sesService.sendPayOrderMessage(order);

        return JsonUtil.mapToJson(order);
    };

    public static final Route dePayOrder = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);
        Order order = orderRepository.dePayOrder(mail, time);

        sesService.sendDePaidOrderMessage(order);

        return JsonUtil.mapToJson(order);
    };

    public static final Route shipOrder =  (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);
        Order order = orderRepository.shipOrder(mail, time);

        sesService.sendShipOrderMessage(order);

        return JsonUtil.mapToJson(order);
    };


    public static final Route deShipOrder = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String mail = getMail(map);
        String time = getOrderDateTime(map);
        Order order = orderRepository.deShipOrder(mail, time);

        sesService.sendDeShippedOrderMessage(order);

        return JsonUtil.mapToJson(order);
    };

    private static Order mapRequestBodyToOrder(String body) {
        try {
            return JsonUtil.mapToOrder(body);
        }catch (IOException e) {
            throw new AdminOrderControllerClientException("request body not allow :" + body);
        }
    }

    private static String getMail(Map<String, String> map) {
        return map.get(Param.ORDER_MAIL);
    }

    private static String getOrderDateTime(Map<String, String> map) {
        return map.get(Param.ORDER_DATE_TIME);
    }

    private static Integer getLimit(Map<String, String> map) {
        try {
            return Integer.valueOf(map.get(Param.ORDER_LIMIT));
        }catch (NumberFormatException e) {
            throw new AdminOrderControllerClientException("order limit number format error");
        }
    }

    private static String getBankInformation(Map<String, String> map) {
        return map.get(Param.ORDER_BANK_INFORMATION);
    }

    private static String getPaidDate(Map<String, String> map) {
        return map.get(Param.ORDER_PAID_DATE);
    }

    private static String getPaidTime(Map<String, String> map) {
        return map.get(Param.ORDER_PAID_TIME);
    }

    private static String getProcessingDate(Map<String, String> map) {
        return map.get(Param.ORDER_PROCESSING_DATE);
    }

    private static String getOwner(Map<String, String> map) {return map.get(Param.ORDER_OWNER);}


    private static String getShippedDate(Map<String, String> map) {
        return map.get(Param.ORDER_SHIPPED_DATE);
    }

    private static String getShippedTime(Map<String, String> map) {
        return map.get(Param.ORDER_SHIPPED_TIME);
    }

}
