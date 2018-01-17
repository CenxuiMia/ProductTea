package com.cenxui.shop.admin.app.controller.report;

import com.cenxui.shop.admin.app.config.DynamoDBConfig;
import com.cenxui.shop.admin.app.controller.AdminCoreController;
import com.cenxui.shop.admin.app.util.Param;
import com.cenxui.shop.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.shop.repositories.order.OrderRepository;
import com.cenxui.shop.util.JsonUtil;
import spark.Route;

import java.util.Map;

public class AdminCashController extends AdminCoreController {

    private static final OrderRepository orderRepository =
            DynamoDBRepositoryService.getOrderRepository(
                    DynamoDBConfig.REGION,
                    DynamoDBConfig.ORDER_TABLE,
                    DynamoDBConfig.PRODUCT_TABLE
            );


    public static final Route getAllCashReport = ((request, response) -> {
        return JsonUtil.mapToJson(orderRepository.getAllCashReport());
    });

    public static final Route getRangeCashReport = ((request, response) -> {
        Map<String, String> map = request.params();
        String fromPaidDate = getReportCashFromDate(map);
        String toPaidDate = getReportCashToDate(map);
        return JsonUtil.mapToJson(orderRepository.getRangeCashReport(fromPaidDate, toPaidDate));
    });

    public static final Route getDailyCashReport = ((request, response) -> {
        Map<String, String> map = request.params();
        String paidDate = getReportCashDate(map);
        return JsonUtil.mapToJson(orderRepository.getDailyCashReport(paidDate));
    });

    private static String getReportCashFromDate(Map<String, String> map) {
        return map.get(Param.REPORT_CASH_FROM_DATE);
    }

    private static String getReportCashToDate(Map<String, String> map) {
        return map.get(Param.REPORT_CASH_TO_DATE);
    }

    private static String getReportCashDate(Map<String, String> map) {
        return map.get(Param.REPORT_CASH_DATE);
    }

}
