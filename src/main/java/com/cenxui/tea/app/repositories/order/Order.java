package com.cenxui.tea.app.repositories.order;

import com.cenxui.tea.app.repositories.product.Product;
import lombok.Data;
import lombok.Setter;
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
    public static final String MAIL ="mail";
    public static final String TIME = "time";
    public static final String PRODUCTS = "products";
    public static final String PURCHASER = "purchaser";
    public static final String MONEY = "money";
    public static final String RECEIVER = "receiver";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";

    public static final String COMMENT = "comment";
    public static final String PAID_DATE = "paidDate";

    public static final String SHIP_DATE = "shipDate";
    public static final String IS_ACTIVE = "isActive";

    String mail; // default user email
    String time =  LocalDateTime.now().toString().substring(0,19);
    List<String> products;
    String purchaser;
    Float money;

    String receiver;
    String phone;
    String address;
    String comment;

    String paidDate;
    String shipDate;
    Boolean isActive;


    public static Order of(String mail,
                           List<String> products,
                           String purchaser,
                           Float money,
                           String receiver,
                           String phone,
                           String address,
                           String comment,
                           String paidDate,
                           String shipDate,
                           Boolean isActive) {

        return new Order(mail, products, purchaser, money, receiver, phone, address, comment, paidDate, shipDate, isActive);
    }


}
