package com.cenxui.shop.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.cenxui.shop.repositories.order.OrderRepository;
import com.cenxui.shop.repositories.product.ProductRepository;
import com.cenxui.shop.repositories.user.UserRepository;

public class DynamoDBRepositoryService {

    public static OrderRepository getOrderRepository(
                                                     String region,
                                                     String orderTableName,
                                                     String paidIndex,
                                                     String processingIndex,
                                                     String shippedIndex,
                                                     String productTableName) {
        Table orderTable = DynamoDBManager.getDynamoDB(region).getTable(orderTableName);
        Table productTable = DynamoDBManager.getDynamoDB(region).getTable(productTableName);
        DynamoDBOrderBaseRepository orderRepository =
                new DynamoDBOrderBaseRepository(orderTable, paidIndex, processingIndex, shippedIndex);
        DynamoDBProductRepository productRepository =
                new DynamoDBProductRepository(productTable);

        return  new DynamoDBOrderRepository(orderRepository, productRepository);
    }

    public static OrderRepository getOrderRepositoryLocal(String region, String url,
                                                          String orderTableName,
                                                          String paidIndex,
                                                          String processingIndex,
                                                          String shippedIndex,
                                                          String productTableName) {
        Table orderTable = DynamoDBManager.getDynamoDBLocal(region, url).getTable(orderTableName);
        Table productTable = DynamoDBManager.getDynamoDBLocal(region, url).getTable(productTableName);

        DynamoDBOrderBaseRepository orderRepository =
                new DynamoDBOrderBaseRepository(orderTable, paidIndex, processingIndex, shippedIndex);
        DynamoDBProductRepository productRepository =
                new DynamoDBProductRepository(productTable);

        return new DynamoDBOrderRepository(orderRepository, productRepository);
    }

    public static ProductRepository getProductRepository(String region, String productTableName) {

        Table productTable = DynamoDBManager.getDynamoDB(region).getTable(productTableName);

        return new DynamoDBProductRepository(productTable);
    }

    public static ProductRepository getProductRepositoryLocal(String region, String url, String productTableName) {

        Table productTable = DynamoDBManager.getDynamoDBLocal(region, url).getTable(productTableName);

        return new DynamoDBProductRepository(productTable);

    }

    public static UserRepository getUserRepository(String region, String userTableName) {

        Table userTable = DynamoDBManager.getDynamoDB(region).getTable(userTableName);

        return new DynamoDBUserRepository(userTable);
    }

    public static UserRepository getUserRepositoryLocal(String region, String url, String userTableName) {

        Table userTable = DynamoDBManager.getDynamoDBLocal(region, url).getTable(userTableName);

        return new DynamoDBUserRepository(userTable);
    }
}
