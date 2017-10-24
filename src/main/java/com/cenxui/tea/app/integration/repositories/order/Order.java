package com.cenxui.tea.app.integration.repositories.order;

import com.cenxui.tea.app.integration.repositories.catagory.Product;
import lombok.Data;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Value(staticConstructor = "of")
public class Order {
    public static final String TIME = "time";
    public static final String MAIL ="mail";
    public static final String PURCHASER = "purchaser";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String PRODUCTS = "products";
    public static final String COMMENTS = "comments";

    private final LocalDateTime timeStamp =  LocalDateTime.now();

    private final String time = timeStamp.toString();

    String mail; // default user email
    List<Map<Product, Integer>> products;
    String purchaser;
    String phone;
    String address;
    String comments;


    /**
     * the id is concat with time and mail
     * @return id
     */
    public String getId() {
        return time.concat(mail);
    }

}
