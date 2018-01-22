package com.cenxui.shop.repositories.order;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;


/**
 *
 */
@Value(staticConstructor = "of")
public class Order {
    public static final String MAIL ="mail";
    public static final String ORDER_DATE_TIME = "orderDateTime";
    public static final String PRODUCTS = "products";
    public static final String PURCHASER = "purchaser";
    public static final String PURCHASER_PHONE = "purchaserPhone";
    public static final String SHIPPING_COST = "shippingCost";
    public static final String PRODUCTS_PRICE = "productsPrice";
    public static final String PRICE = "price";
    public static final String PAYMENT_METHOD = "paymentMethod";
    public static final String BANK_INFORMATION = "bankInformation";
    public static final String RECEIVER = "receiver";
    public static final String RECEIVER_PHONE = "receiverPhone";
    public static final String SHIPPING_WAY = "shippingWay";
    public static final String SHIPPING_ADDRESS = "shippingAddress";
    public static final String COMMENT = "comment";
    public static final String PAID_DATE = "paidDate";
    public static final String PAID_TIME = "paidTime";
    public static final String PROCESSING_DATE = "processingDate";
    public static final String SHIPPED_DATE = "shippedDate";
    public static final String SHIPPED_TIME= "shippedTime";
    public static final String IS_ACTIVE = "isActive";
    public static final String OWNER = "owner";

    String mail; // default user email
    String orderDateTime;
    List<String> products;
    String purchaser;
    String purchaserPhone;
    Integer shippingCost;
    Integer productsPrice;
    String activity;
    Integer price;
    String paymentMethod;
    String bankInformation;

    String receiver;
    String receiverPhone;
    String shippingWay;
    String shippingAddress;
    String comment;

    String paidDate;
    String paidTime;
    String processingDate; //todo add owner
    String shippedDate;
    String shippedTime;
    Boolean isActive;
    String owner;
}
