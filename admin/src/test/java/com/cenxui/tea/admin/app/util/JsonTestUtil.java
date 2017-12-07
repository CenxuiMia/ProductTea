package com.cenxui.tea.admin.app.util;

import com.cenxui.tea.app.repositories.order.report.CashReport;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonTestUtil {
    public static CashReport mapToCashReport(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, CashReport.class);
    }


    public static OrderTestResult mapToOrderTestResult(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, OrderTestResult.class);

    }

    public static PaidOrderTestResult mapToPaidOrderTest(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, PaidOrderTestResult.class);
    }

    public static ProcessingOrderTestResult mapToProcessingOrderTestResult(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, ProcessingOrderTestResult.class);
    }

    public static ShippedOrderTestResult mapToShippedOrderTestResult(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, ShippedOrderTestResult.class);
    }
}
