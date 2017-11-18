package com.cenxui.tea.app.util;

import com.cenxui.tea.app.repositories.order.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class JsonUtil {
    public static String mapToJson(Order order) {
        ObjectMapper mapper = new ObjectMapper();
        String orderJson = null;
        try {
            orderJson = mapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //todo
        }
        return orderJson;
    }

    public static String mapToJson(List<Order> orders) {
        ObjectMapper mapper = new ObjectMapper();
        String ordersJson = null;
        try {
            ordersJson = mapper.writeValueAsString(orders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //todo
        }
        return ordersJson;
    }

    public static Order mapToOrder(String orderJson) {
        ObjectMapper mapper = new ObjectMapper();
        Order order = null;
        try {
            order = mapper.readValue(orderJson, Order.class);
        } catch (IOException e) {
            e.printStackTrace();
            //todo
        }
        return order;
    }
}
