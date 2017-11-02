package com.cenxui.tea.app.config;

public class DynamoDBConfig {
    public static final String URL;
    public static final String REGION = "us-west-2";
    public static final String USER_TABLE = "user";
    public static final String ORDER_TABLE = "order";
    public static final String PRODUCT_TABLE = "product";
    static {
        String url = System.getenv("Region");
        URL = url != null ? url : "http://localhost:8000";

        String region = System.getenv("");
        //TODO

    }




}
