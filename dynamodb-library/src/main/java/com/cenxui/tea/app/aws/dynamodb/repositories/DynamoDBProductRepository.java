package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.cenxui.tea.app.aws.dynamodb.exceptions.map.product.ProductJsonMapException;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.product.ProductRepository;
import com.cenxui.tea.app.util.JsonUtil;

import java.util.*;

final class DynamoDBProductRepository implements ProductRepository {

    private Table productTable;

    DynamoDBProductRepository(Table table) {
        this.productTable = table;

    }

    @Override
    public List<Product> getProductsByTag(String tag) {
        //todo
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public List<Product> getAllProducts() {
        ItemCollection itemCollection = productTable.scan();
        return mapToProducts(itemCollection);
    }

    @Override
    public List<Product> getProductsByPrice(Float price) {
        //todo
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public List<Product> getProductsByName(String name) {
        QuerySpec spec = new QuerySpec()
                .withHashKey(Product.NAME, name);

        ItemCollection collection = productTable.query(spec);

        List<Product> products = mapToProducts(collection);

        return products; //todo throw exception
    }

    @Override
    public Product getProductByNameVersion(String name, String version) {
        //todo
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public Float getProductPrice(String name, String version) {
        QuerySpec querySpec = new QuerySpec()
                .withHashKey(Product.NAME, name)
                .withRangeKeyCondition(new RangeKeyCondition(Product.VERSION).eq(version));

        ItemCollection collection = productTable.query(querySpec);

        List<Product> products = mapToProducts(collection);

        Product product = products.get(0); // todo throw exception;

        return product.getPrice();
    }

    private List<Product> mapToProducts(ItemCollection<Item> collection) {
        List<Product> products = new ArrayList<>();
        collection.forEach(
                (s) -> {
                    String productJson = s.toJSON();
                    try {
                        products.add(JsonUtil.mapToProduct(productJson));
                    }catch (Exception e) {
                        throw new ProductJsonMapException(productJson);
                    }
                }
        );
        return products;
    }
}
