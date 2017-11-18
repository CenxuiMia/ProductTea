package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.cenxui.tea.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.product.ProductRepository;
import com.cenxui.tea.app.util.JsonUtil;

import java.util.*;
import java.util.stream.Collectors;

final class DynamoDBProductRepository implements ProductRepository {

    /**
     * cache in list
     */

    private final List<Product> products;

    /**
     * cache in Json
     */
    private final String productsJson;

    //cache all products
    DynamoDBProductRepository() {

        Table table = DynamoDBManager.getDynamoDB().getTable(DynamoDBConfig.PRODUCT_TABLE);

        ItemCollection<ScanOutcome> collection = table.scan();

        final List<Product> products = mapToProducts(collection);

        this.products = Collections.unmodifiableList(products);

        this.productsJson = JsonUtil.mapProductsToJson(products);

    }

    private List<Product> mapToProducts(ItemCollection<ScanOutcome> collection) {
        List<Product> products = new ArrayList<>();
        collection.forEach(
                (s) -> {
                    products.add(JsonUtil.mapToProduct(s.toJSON()));
                }
        );
        return products;
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
    public List<Product> getProductsByPrice(Float price) {
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

    @Override
    public String getAllProductsJSON() {
        return productsJson;
    }
}
