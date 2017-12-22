package com.cenxui.shop.admin.app.config;

public class DynamoDBConfig {

    public static final boolean isLocal = false;
    /**
     * This will be used if dynamodb id local version
     */
    public static final String URL = "http://localhost:8000";
    public static final String REGION = "ap-northeast-1";

    public static final String USER_TABLE = "censUser"; //user pool

    public static final String ORDER_TABLE = "teaOrder";  //todo modify
    public static final String PRODUCT_TABLE = "teaProduct"; //todo modify

    public static final String ORDER_PROCESSING_INDEX = "processingIndex";
    public static final String ORDER_SHIPPED_INDEX = "shippedIndex";
    public static final String ORDER_PAID_INDEX = "paidIndex";
}
