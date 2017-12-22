package com.cenxui.shop.admin.app.application;

import com.cenxui.shop.admin.app.util.JsonTestUtil;
import com.cenxui.shop.repositories.order.CashReport;
import com.cenxui.shop.util.Http;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ReportCashPathTest {

    private String url;

    @Before
    public void setUp() {
        url = "http://localhost:9000/admin/report/cash";
    }

    @Test
    public void getAllCashReport() throws IOException {
        String body = Http.getResult(url + "/all");

        CashReport report = JsonTestUtil.mapToCashReport(body);

        System.out.println("revenue" + report.getRevenue());

    }

    @Test
    public void getDailyCashReport() throws IOException {
        String body = Http.getResult(url + "/daily/2017-12-04");

        CashReport report = JsonTestUtil.mapToCashReport(body);

        System.out.println("revenue" + report.getRevenue());
    }

    @Test
    public void getRangeCashReport() throws IOException {
        String body = Http.getResult(url + "/range/2017-12-03/2017-12-06");

        CashReport report = JsonTestUtil.mapToCashReport(body);

        System.out.println("revenue" + report.getRevenue());
    }

}
