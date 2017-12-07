package com.cenxui.tea.admin.app.service.report;

import com.cenxui.tea.admin.app.config.DynamoDBConfig;
import com.cenxui.tea.admin.app.service.AdminCoreController;
import com.cenxui.tea.admin.app.util.Param;
import com.cenxui.tea.app.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.tea.app.repositories.order.OrderRepository;
import com.cenxui.tea.app.util.JsonUtil;
import spark.Route;

import java.util.Map;

public class AdminCashController extends AdminCoreController {

    private static final OrderRepository orderRepository =
            DynamoDBRepositoryService.getOrderRepository(
                    DynamoDBConfig.REGION,
                    DynamoDBConfig.ORDER_TABLE,
                    DynamoDBConfig.ORDER_PAID_INDEX,
                    DynamoDBConfig.ORDER_PROCESSING_INDEX,
                    DynamoDBConfig.ORDER_SHIPPED_INDEX,
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
