package com.cenxui.shop.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.cenxui.shop.repositories.coupon.CouponRepository;
import com.cenxui.shop.repositories.order.OrderRepository;
import com.cenxui.shop.repositories.point.PointRepository;
import com.cenxui.shop.repositories.product.ProductRepository;
import com.cenxui.shop.repositories.user.UserRepository;

public class DynamoDBRepositoryService {

    public static OrderRepository getOrderRepository(
                                                     String region,
                                                     String orderTableName,
                                                     String productTableName,
                                                     String couponTableName) {
        Table orderTable = DynamoDBManager.getDynamoDB(region).getTable(orderTableName);
        Table productTable = DynamoDBManager.getDynamoDB(region).getTable(productTableName);
        Table couponTable = DynamoDBManager.getDynamoDB(region).getTable(couponTableName);

        DynamoDBOrderBaseRepository orderRepository =
                new DynamoDBOrderBaseRepository(orderTable);
        DynamoDBProductRepository productRepository =
                new DynamoDBProductRepository(productTable);
        DynamoDBCouponRepository couponRepository =
                new DynamoDBCouponRepository(couponTable);

        return  new DynamoDBOrderRepository(orderRepository, productRepository, couponRepository);
    }

    public static OrderRepository getOrderRepositoryLocal(String region, String url,
                                                          String orderTableName,
                                                          String productTableName,
                                                          String couponTableName) {
        Table orderTable = DynamoDBManager.getDynamoDBLocal(region, url).getTable(orderTableName);
        Table productTable = DynamoDBManager.getDynamoDBLocal(region, url).getTable(productTableName);
        Table couponTable = DynamoDBManager.getDynamoDBLocal(region, url).getTable(couponTableName);

        DynamoDBOrderBaseRepository orderRepository =
                new DynamoDBOrderBaseRepository(orderTable);
        DynamoDBProductRepository productRepository =
                new DynamoDBProductRepository(productTable);
        DynamoDBCouponRepository couponRepository =
                new DynamoDBCouponRepository(couponTable);

        return  new DynamoDBOrderRepository(orderRepository, productRepository, couponRepository);
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

    public static CouponRepository getCouponRepository(String region, String couponTableName) {
        Table couponTable = DynamoDBManager.getDynamoDB(region).getTable(couponTableName);
        return new DynamoDBCouponRepository(couponTable);
    }

    public static CouponRepository getCouponRepositoryLocal(String region, String url, String couponTableName) {
        Table couponTable = DynamoDBManager.getDynamoDBLocal(region, url).getTable(couponTableName);
        return new DynamoDBCouponRepository(couponTable);
    }

    public static PointRepository getPointRepository(String region, String pointTableName) {
        Table pointTable = DynamoDBManager.getDynamoDB(region).getTable(pointTableName);
        return new DynamoDBPointRepository(pointTable);
    }

    public static PointRepository getPointRepositoryLocal(String region, String url, String pointTableName) {
        Table pointTable = DynamoDBManager.getDynamoDBLocal(region, url).getTable(pointTableName);
        return new DynamoDBPointRepository(pointTable);
    }

}
