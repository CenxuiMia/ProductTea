package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.cenxui.tea.app.aws.dynamodb.exceptions.map.product.ProductJsonMapException;
import com.cenxui.tea.app.aws.dynamodb.util.ItemUtil;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.product.ProductKey;
import com.cenxui.tea.app.repositories.product.ProductRepository;
import com.cenxui.tea.app.repositories.product.ProductResult;
import com.cenxui.tea.app.util.JsonUtil;

import java.util.*;

final class DynamoDBProductRepository implements ProductRepository {

    private Table productTable;

    DynamoDBProductRepository(Table table) {
        this.productTable = table;

    }

    @Override
    public ProductResult getProductsByTag(String tag) {
        //todo
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public ProductResult getAllProducts() {
        ItemCollection itemCollection = productTable.scan();
        List<Product> products = mapToProducts(itemCollection);
        ProductKey productKey = getScanOutcomeLastKey(itemCollection);

        return ProductResult.of(products, productKey);
    }


    @Override
    public ProductResult getProductsByPrice(Float price) {
        //todo
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public ProductResult getProductsByName(String name) {
        QuerySpec spec = new QuerySpec()
                .withHashKey(Product.NAME, name);

        ItemCollection collection = productTable.query(spec);

        List<Product> products = mapToProducts(collection);

         //todo throw exception

        throw new UnsupportedOperationException("not yet");
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

        Product product = products.get(0); // todo throw exception; posible not exist

        return product.getPrice();
    }

    @Override
    public boolean addProduct(Product product) {
        PutItemSpec spec = new PutItemSpec()
                .withItem(ItemUtil.getProductItem(product));
        productTable.putItem(spec);

        return true;
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

    private ProductKey getScanOutcomeLastKey(ItemCollection<ScanOutcome> collection) {
        ScanOutcome scanOutcome = collection.getLastLowLevelResult();
        Map<String, AttributeValue> lastKeyEvaluated = scanOutcome.getScanResult().getLastEvaluatedKey();

        ProductKey productKey = null;

        if (lastKeyEvaluated != null) { //null if it is last one
            productKey = ProductKey.of(
                    lastKeyEvaluated.get(Product.NAME).getS(), lastKeyEvaluated.get(Product.VERSION).getS());
        }

        return productKey;
    }

}
