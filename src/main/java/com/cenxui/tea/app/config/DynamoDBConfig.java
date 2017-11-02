package com.cenxui.tea.app.config;

public class DynamoDBConfig {
    public static final String URL;
    public static final String REGION = "ap-northeast-1";
    public static final String USER_TABLE = "teaUser";
    public static final String ORDER_TABLE = "teaOrder";
    public static final String PRODUCT_TABLE = "teaProduct";
    static {
        String url = System.getenv("Region");
        URL = url != null ? url : "http://localhost:8000";

        String region = System.getenv("");
        //TODO

    }




}
