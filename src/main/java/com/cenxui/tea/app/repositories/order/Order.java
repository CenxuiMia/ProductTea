package com.cenxui.tea.app.repositories.order;

import com.cenxui.tea.app.repositories.product.Product;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Value(staticConstructor = "of")
public class Order {
    public static final String TIME = "time";
    public static final String MAIL ="mail";
    public static final String PURCHASER = "purchaser";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String PRODUCTS = "products";
    public static final String COMMENTS = "comments";
    public static final String IS_PAID = "isPaid";
    public static final String IS_ACTIVE = "isActive";

    private final LocalDateTime timeStamp =  LocalDateTime.now();

    private final String time = timeStamp.toString().substring(0,19);

    String mail; // default user email
    List<Map<Product, Integer>> products;
    String purchaser;
    String phone;
    String address;
    String comments;
    Boolean isPaid;
    Boolean isActive;

    //TODO please make sure order primary key and its attribute

    /**
     * the id is concat with mail and time
     * @return id
     */
    public String getId() {
        return mail.concat(time);
    }

}
