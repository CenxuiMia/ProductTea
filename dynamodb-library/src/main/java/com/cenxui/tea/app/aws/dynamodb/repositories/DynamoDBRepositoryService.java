package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.cenxui.tea.app.repositories.order.OrderRepository;
import com.cenxui.tea.app.repositories.product.ProductRepository;
import com.cenxui.tea.app.repositories.user.UserRepository;

public class DynamoDBRepositoryService {

    private static final DynamoDBUserRepository userRepository = new DynamoDBUserRepository();

    public static OrderRepository getOrderRepository(
                                                     String region,
                                                     String orderTableName,
                                                     String paidIndex,
                                                     String processingIndex,
                                                     String shippedIndex,
                                                     String productTableName) {
        Table orderTable = DynamoDBManager.getDynamoDB(region).getTable(orderTableName);
        Table productTable = DynamoDBManager.getDynamoDB(region).getTable(productTableName);
        DynamoDBOrderRepository orderRepository =
                new DynamoDBOrderRepository(orderTable, paidIndex, processingIndex, shippedIndex);
        DynamoDBProductRepository productRepository =
                new DynamoDBProductRepository(productTable);

        return  new DynamoDBOrderRepositoryWrapper(orderRepository, productRepository);
    }

    public static OrderRepository getOrderRepositoryLocal(String region, String url,
                                                          String orderTableName,
                                                          String paidIndex,
                                                          String processingIndex,
                                                          String shippedIndex,
                                                          String productTableName) {
        Table orderTable = DynamoDBManager.getDynamoDBLocal(region, url).getTable(orderTableName);
        Table productTable = DynamoDBManager.getDynamoDBLocal(region, url).getTable(productTableName);

        DynamoDBOrderRepository orderRepository =
                new DynamoDBOrderRepository(orderTable, paidIndex, processingIndex, shippedIndex);
        DynamoDBProductRepository productRepository =
                new DynamoDBProductRepository(productTable);

        return new DynamoDBOrderRepositoryWrapper(orderRepository, productRepository);
    }

    public static ProductRepository getProductRepository(String region, String productTableName) {

        Table productTable = DynamoDBManager.getDynamoDB(region).getTable(productTableName);

        return new DynamoDBProductRepository(productTable);
    }

    public static ProductRepository getProductRepositoryLocal(String region, String url, String productTableName) {

        Table productTable = DynamoDBManager.getDynamoDBLocal(region, url).getTable(productTableName);

        return new DynamoDBProductRepository(productTable);

    }

    public static UserRepository getUserRepository() {
        return userRepository;
    }
}
