package com.cenxui.tea.app.repositories.order;

import com.cenxui.tea.app.repositories.product.Product;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 *  todo
 *  order need to make sure the sum
 *  the order isPaid
 *  the order isActive
 *  the order time
 */
@Value
public class Order {
    public static final String TIME = "time";
    public static final String MAIL ="mail";
    public static final String PURCHASER = "purchaser";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String PRODUCTS = "products";
    public static final String COMMENTS = "comments";
    public static final String PAID_DATE = "paidDate";
    public static final String SHIP_DATE = "shipDate";
    public static final String IS_ACTIVE = "isActive";

    String mail; // default user email
    String time;
    List<String> products;
    String purchaser;
    String phone;
    String address;
    String comments;
    String paidDate;
    String shipDate;
    Boolean isActive;


    public static Order of(String mail,
                           List<String> products,
                           String purchaser,
                           String phone,
                           String address,
                           String comments,
                           String paidDate,
                           String shipDate,
                           Boolean isActive) {
        final LocalDateTime timeStamp =  LocalDateTime.now();
        String time = timeStamp.toString().substring(0,19);
        String date = timeStamp.toLocalDate().toString();
        return new Order(mail, time, products, purchaser, phone, address, comments, paidDate, shipDate, isActive);
    }

}
