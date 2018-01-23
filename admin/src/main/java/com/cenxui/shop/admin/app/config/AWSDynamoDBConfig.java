package com.cenxui.shop.admin.app.config;

public class AWSDynamoDBConfig {

    public static final boolean isLocal = false;
    /**
     * This will be used if dynamodb id local version
     */
    public static final String URL = "http://localhost:8000";
    public static final String REGION = "ap-northeast-1";

    public static final String USER_TABLE = "censUser"; //user pool

    public static final String ORDER_TABLE = "teaOrder";  //todo modify
    public static final String PRODUCT_TABLE = "teaProduct"; //todo modify
}
