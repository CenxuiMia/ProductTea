package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.cenxui.tea.app.aws.dynamodb.exceptions.map.product.ProductJsonMapException;
import com.cenxui.tea.app.aws.dynamodb.util.ItemUtil;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.product.ProductKey;
import com.cenxui.tea.app.repositories.product.ProductRepository;
import com.cenxui.tea.app.repositories.product.Products;
import com.cenxui.tea.app.util.JsonUtil;

import java.util.*;

final class DynamoDBProductRepository implements ProductRepository {

    private Table productTable;

    DynamoDBProductRepository(Table table) {
        this.productTable = table;
    }

    @Override
    public Products getProductsByTag(String tag) {
        //todo
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public Products getAllProducts() {
        ItemCollection<ScanOutcome> itemCollection = productTable.scan();
        List<Product> products = mapScanOutcomeToProducts(itemCollection);
        ProductKey productKey = getScanOutcomeLastKey(itemCollection);

        return Products.of(products, productKey);
    }

    @Override
    public Products getAllProductsProjectIntroSmallImagePriceTag() {

        //todo add field
        ScanSpec scanSpec = new ScanSpec()
                .withProjectionExpression(
                        Product.PRODUCT_NAME + "," +
                        Product.VERSION +"," +
                        Product.SMALL_IMAGE + "," +
                        Product.INTRODUCTION + "," +
                        Product.PRICE + "," +
                        Product.TAG );

        ItemCollection<ScanOutcome> itemCollection = productTable.scan(scanSpec);
        List<Product> products = mapScanOutcomeToProducts(itemCollection);
        ProductKey productKey = getScanOutcomeLastKey(itemCollection);

        return Products.of(products, productKey);
    }

    @Override
    public Products getProductsByPrice(Float price) {
        //todo
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public Products getProductsByProductName(String name) {
        QuerySpec spec = new QuerySpec()
                .withHashKey(Product.PRODUCT_NAME, name);

        ItemCollection<QueryOutcome> collection = productTable.query(spec);

        List<Product> products = mapQueryOutcomeToProducts(collection);

        ProductKey productKey = getQueryOutcomeLastKey(collection);

        return Products.of(products, productKey);
    }

    @Override
    public Product getProductByProductNameVersion(String productName, String version) {
        QuerySpec spec = new QuerySpec()
                .withHashKey(Product.PRODUCT_NAME, productName)
                .withRangeKeyCondition( new RangeKeyCondition(Product.VERSION).eq(version));

        ItemCollection<QueryOutcome> collection = productTable.query(spec);
        List<Product> productList = mapQueryOutcomeToProducts(collection);

        if (productList.size() == 1) {
            return productList.get(0);
        }

        return null;
    }

    @Override
    public Float getProductPrice(String productName, String version) {
        QuerySpec querySpec = new QuerySpec()
                .withHashKey(Product.PRODUCT_NAME, productName)
                .withRangeKeyCondition(new RangeKeyCondition(Product.VERSION).eq(version));

        ItemCollection<QueryOutcome> collection = productTable.query(querySpec);

        List<Product> products = mapQueryOutcomeToProducts(collection);

        if (products.size() == 1) {
            return products.get(0).getPrice();
        }
        return null;
    }

    @Override
    public Product addProduct(Product product) {
        PutItemSpec spec = new PutItemSpec()
                .withItem(ItemUtil.getProductItem(product));
        productTable.putItem(spec);
        //todo not stable
        return product;
    }

    private List<Product> mapQueryOutcomeToProducts(ItemCollection<QueryOutcome> collection) {
        List<Product> products = new ArrayList<>();
        collection.forEach(
                (s) -> {
                    products.add(mapToProduct(s.toJSON()));
                }
        );
        return products;
    }


    private List<Product> mapScanOutcomeToProducts(ItemCollection<ScanOutcome> collection) {
        List<Product> products = new ArrayList<>();
        collection.forEach(
                (s) -> {
                    products.add(mapToProduct(s.toJSON()));
                }
        );
        return products;
    }

    private Product mapToProduct(String productJson) {
        try {
            return JsonUtil.mapToProduct(productJson);
        }catch (Throwable e) {
            throw new ProductJsonMapException(productJson);
        }
    }

    private ProductKey getScanOutcomeLastKey(ItemCollection<ScanOutcome> collection) {
        ScanOutcome scanOutcome = collection.getLastLowLevelResult();
        Map<String, AttributeValue> lastKeyEvaluated = scanOutcome.getScanResult().getLastEvaluatedKey();

        ProductKey productKey = null;

        if (lastKeyEvaluated != null) { //null if it is last one
            productKey = ProductKey.of(
                    lastKeyEvaluated.get(Product.PRODUCT_NAME).getS(), lastKeyEvaluated.get(Product.VERSION).getS());
        }

        return productKey;
    }

    private ProductKey getQueryOutcomeLastKey(ItemCollection<QueryOutcome> collection) {
        QueryOutcome queryOutcome = collection.getLastLowLevelResult();
        Map<String, AttributeValue> lastKeyEvaluated = queryOutcome.getQueryResult().getLastEvaluatedKey();

        ProductKey productKey = null;

        if (lastKeyEvaluated != null) { //null if it is last one
            productKey = ProductKey.of(
                    lastKeyEvaluated.get(Product.PRODUCT_NAME).getS(), lastKeyEvaluated.get(Product.VERSION).getS());
        }

        return productKey;
    }


}
