package com.cenxui.tea.app.repositories.order;

import com.cenxui.tea.app.aws.dynamodb.exceptions.order.OrderMapJsonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public static final String ADDRESS = "address";

    public static final String COMMENT = "comment";
    public static final String PAID_DATE = "paidDate";
    public static final String SHIP_DATE = "shippedDate";
    public static final String PROCESS_DATE = "processingDate";
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
    String processingDate;
    String shippedDate;
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
                           String processingDate,
                           String shippedDate,
                           Boolean isActive) {

        return new Order(
                mail, products, purchaser, money, receiver, phone,
                address, comment, paidDate, processingDate, shippedDate,
                isActive);
    }

    private boolean isEmpty(String s) {
        if (s == null || s.isEmpty()) return true;
        return false;
    }

    @Override
    public String toString() {
        return toJson();
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new OrderMapJsonException(this);
        }
    }
}
