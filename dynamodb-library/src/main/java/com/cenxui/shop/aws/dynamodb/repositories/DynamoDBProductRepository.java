package com.cenxui.shop.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.*;
import com.cenxui.shop.repositories.product.*;

import java.util.*;

/**
 * product table transaction layer
 */
final class DynamoDBProductRepository implements ProductRepository {

    private final ProductBaseRepository productBaseRepository;

    DynamoDBProductRepository(Table table) {
        productBaseRepository = new DynamoDBProductBaseRepository(table);

    }

    @Override
    public Products getAllProducts() {
        return productBaseRepository.getAllProducts();
    }

    @Override
    public Products getAllSortedProductsPartial() {
        return productBaseRepository.getAllSortedProductsPartial();
    }

    @Override
    public Products getProductsByTag(String tag) {
        return productBaseRepository.getProductsByTag(tag);
    }

    @Override
    public Products getProductsByPrice(Integer price) {
        return productBaseRepository.getProductsByPrice(price);
    }

    @Override
    public Products getProductsByProductName(String productName) {
        return productBaseRepository.getProductsByProductName(productName);
    }

    @Override
    public Product getProductByProductNameVersion(String productName, String version) {
        return productBaseRepository.getProductByProductNameVersion(productName, version);
    }

    @Override
    public Product addProduct(Product product) {
        return productBaseRepository.addProduct(product);
    }

    @Override
    public boolean deleteProduct(String productName, String version) {
        return productBaseRepository.deleteProduct(productName, version);
    }

    @Override
    public Price getProductPrice(String name, String version) {
        return productBaseRepository.getProductPrice(name, version);
    }

    @Override
    public Price getProductsPrice(List<String> products) {
        return productBaseRepository.getProductsPrice(products);
    }
}
