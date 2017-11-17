package com.cenxui.tea.app.config;


/**
 * The dynamodb configuration file
 */
public class DynamoDBConfig {
    /**
     * set up dynamodb is local or not
     */
    public static final boolean isLocal = false;
    /**
     * This will be used if dynamodb id local version
     */
    public static final String URL = "http://localhost:8000";
    public static final String REGION = "ap-northeast-1";

    public static final String USER_TABLE = "teaUser";
    public static final String ORDER_TABLE = "teaOrder";
    public static final String PRODUCT_TABLE = "teaProduct";

    public static final String ORDER_PROCESSING_INDEX = "processsingIndex";
    public static final String ORDER_SHIPPED_INDEX = "shippedIndex";
    public static final String ORDER_PAID_INDEX = "paidIndex";




}
