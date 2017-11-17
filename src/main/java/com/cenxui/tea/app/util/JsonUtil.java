package com.cenxui.tea.app.util;

import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {
    public static Order getOrder(String orderJson) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(orderJson, Order.class);
    }

    public static String getJson(Order order) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(order);
    }

    public static Product getProduct(String productJson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(productJson, Product.class);
    }

    public static String getJson(Product product) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(product);
    }

    public static User getUser(String userJson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(userJson, User.class);
    }

    public static String getJson(User user) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(user);
    }
}
