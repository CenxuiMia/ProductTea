package com.cenxui.tea.app.util;

import com.cenxui.tea.app.repositories.order.Order;
import com.cenxui.tea.app.repositories.order.OrderResult;
import com.cenxui.tea.app.repositories.product.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonUtil {

    public static String mapOrderResultToJson(OrderResult orderResult) {
        ObjectMapper mapper = new ObjectMapper();
        String ordersJson;
        try {
            ordersJson = mapper.writeValueAsString(orderResult);
        } catch (JsonProcessingException e) {
            throw new ObjectToJsonException(orderResult);
        }
        return ordersJson;
    }

    public static Order mapToOrder(String orderJson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(orderJson, Order.class);

        return order;
    }

    public static Product mapToProduct(String productJson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.readValue(productJson, Product.class);

        return product;
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
}
