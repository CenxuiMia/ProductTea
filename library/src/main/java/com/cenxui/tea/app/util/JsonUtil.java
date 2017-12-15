package com.cenxui.tea.app.util;

import com.cenxui.tea.app.images.ProductImage;
import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.user.User;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    public static Order mapToOrder(String orderJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(orderJson, Order.class);

        return order;
    }

    public static Product mapToProduct(String productJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.readValue(productJson, Product.class);

        return product;
    }

    public static User mapToUser(String userJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(userJson, User.class);
        return user;
    }

    public static ProductImage mapToProductImage(String productImageJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ProductImage productImage = mapper.readValue(productImageJson, ProductImage.class);
        return productImage;
    }

    public static String mapToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String objectJson;
        try {
            objectJson = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ObjectToJsonException(object);
        }
        return objectJson;
    }

    public static String mapToJsonIgnoreNull(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        String objectJson;
        try {
            objectJson = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ObjectToJsonException(object);
        }
        return objectJson;
    }

}
