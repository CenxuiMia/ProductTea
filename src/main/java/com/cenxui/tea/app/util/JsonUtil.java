package com.cenxui.tea.app.util;

import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.product.Product;
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

    public static String mapOrdersToJson(List<Order> orders) {
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

    public static Product mapToProduct(String productJson) {
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        try {
            product = mapper.readValue(productJson, Product.class);
        } catch (IOException e) {
            e.printStackTrace();
            //todo
        }
        return product;
    }

    public static String mapProductsToJson(List<Product> products) {
        ObjectMapper mapper = new ObjectMapper();
        String productsJson = null;
        try {
            productsJson = mapper.writeValueAsString(products);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //todo
        }
        return productsJson;
    }
}
