package com.cenxui.tea.app.repositories.order;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;


/**
 * **note if you modify fields please checkout the ItemUtil to make sure if there is something that should be add to.
 *
 *  todo
 *  order need to make sure the sum
 *  the order isPaid
 *  the order isActive
 *  the order time
 */
@Value
public class Order {
    public static final String MAIL ="mail";
    public static final String ORDER_DATE_TIME = "orderDateTime";
    public static final String PRODUCTS = "products";
    public static final String PURCHASER = "purchaser";
    public static final String CURRENCY = "currency";
    public static final String PRICE = "price";
    public static final String PAYMENT_METHOD = "paymentMethod";
    public static final String RECEIVER = "receiver";
    public static final String PHONE = "phone";
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
    String orderDateTime =  LocalDateTime.now().toString().substring(0,19); //todo
    List<String> products;
    String purchaser;
    String currency;
    Float price;
    String paymentMethod;

    String receiver;
    String phone;
    String shippingWay;
    String shippingAddress;
    String comment;

    String paidDate;
    String paidTime;
    String processingDate; //todo add owner
    String shippedDate;
    String shippedTime;
    Boolean isActive;
    String owner = "com.cenxui.app.admin";

    public static Order of(String mail,
                           List<String> products,
                           String purchaser,
                           String currency,
                           Float price,
                           String paymentMethod,
                           String receiver,
                           String phone,
                           String shippingWay,
                           String shippingAddress,
                           String comment,
                           String paidDate,
                           String paidTime,
                           String processingDate,
                           String shippedDate,
                           String shippedTime,
                           Boolean isActive) {

        return new Order(
                mail, products, purchaser, currency, price, paymentMethod, receiver, phone, shippingWay,
                shippingAddress, comment, paidDate, paidTime, processingDate, shippedDate,
                shippedTime, isActive);
    }

}
