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
    public static final String TIME = "time";
    public static final String PRODUCTS = "products";
    public static final String PURCHASER = "purchaser";
    public static final String MONEY = "money";
    public static final String RECEIVER = "receiver";
    public static final String PHONE = "phone";
    public static final String SHIPPING_ADDRESS = "shippingAddress";
    public static final String SHIPPING_WAY = "shippingWay";
    public static final String COMMENT = "comment";
    public static final String PAID_TIME = "paidTime";
    public static final String SHIPPED_TIME= "shippedTime";
    public static final String PROCESSING_DATE = "processingDate";
    public static final String IS_ACTIVE = "isActive";

    String mail; // default user email
    String time =  LocalDateTime.now().toString().substring(0,19); //todo
    List<String> products;
    String purchaser;
    Float money;

    String receiver;
    String phone;
    String shippingWay;
    String shippingAddress;
    String comment;

    String paidTime;
    String processingDate; //todo add owner
    String shippedTime;
    Boolean isActive;
    String owner = "admin";

    public static Order of(String mail,
                           List<String> products,
                           String purchaser,
                           Float money,
                           String receiver,
                           String phone,
                           String shippingWay,
                           String shippingAddress,
                           String comment,
                           String paidTime,
                           String processingDate,
                           String shippedTime,
                           Boolean isActive) {

        return new Order(
                mail, products, purchaser, money, receiver, phone, shippingWay,
                shippingAddress, comment, paidTime, processingDate, shippedTime,
                isActive);
    }

}
