package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.cenxui.tea.app.aws.dynamodb.item.ItemProduct;
import com.cenxui.tea.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.product.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

final class DynamoDBProductRepository implements ProductRepository {

    private final List<Product> products;

    //cache all products
    DynamoDBProductRepository() {

        Table table = DynamoDBManager.getDynamoDB().getTable(DynamoDBConfig.PRODUCT_TABLE);

        final List<Product> products = new ArrayList<>();

        table.scan().forEach(
                (s) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        Product product = mapper.readValue(s.toJSON(), ItemProduct.class).getItem();
                        products.add(product);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );

        this.products = Collections.unmodifiableList(products);
    }

    @Override
    public List<Product> getProductsByTag(String tag) {
        return products.stream().filter(product -> product.getTag().equals(tag)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public List<Product> getProductsByPrice(Integer price) {
        return products.stream().filter(product -> product.getPrice().equals(price)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return products.stream().filter(product -> product.getName().equals(name)).collect(Collectors.toList());
    }

    @Override
    public Product getProductByNameVersion(String name, String version) {
        return products.stream()
                .filter(product -> product.getName().equals(name) && product.getVersion().equals(version))
                .findAny()
                .get();
    }

}
