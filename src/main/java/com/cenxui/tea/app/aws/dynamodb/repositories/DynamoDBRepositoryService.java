package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.cenxui.tea.app.repositories.order.OrderRepository;
import com.cenxui.tea.app.repositories.product.ProductRepository;
import com.cenxui.tea.app.repositories.user.UserRepository;

public class DynamoDBRepositoryService {
    private static final DynamoDBOrderRepository orderRepository = new DynamoDBOrderRepository();

    private static final DynamoDBProductRepository productRepository = new DynamoDBProductRepository();

    private static final DynamoDBUserRepository userRepository = new DynamoDBUserRepository();

    public static OrderRepository getOrderRepository() {
        return  orderRepository;
    }

    public static ProductRepository getProductRepository() {
        return productRepository;
    }

    public static UserRepository getUserRepository() {
        return userRepository;
    }
}
