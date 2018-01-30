package com.cenxui.shop.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.cenxui.shop.aws.dynamodb.exceptions.server.product.ProductPrimaryKeyCannotEmptyException;
import com.cenxui.shop.aws.dynamodb.exceptions.client.product.ProductNotFoundException;
import com.cenxui.shop.aws.dynamodb.util.ItemUtil;
import com.cenxui.shop.aws.dynamodb.exceptions.server.product.ProductJsonMapException;
import com.cenxui.shop.aws.dynamodb.exceptions.client.product.ProductsFormatException;
import com.cenxui.shop.repositories.product.*;
import com.cenxui.shop.util.JsonUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class DynamoDBProductBaseRepository implements ProductBaseRepository {
    private final Table productTable;

    DynamoDBProductBaseRepository(Table table) {
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
    public Products getAllSortedProductsPartial() {

        //todo add field
        ScanSpec scanSpec = new ScanSpec()
                .withProjectionExpression(
                        Product.PRODUCT_NAME + "," +
                                Product.VERSION +"," +
                                Product.SMALL_IMAGE + "," +
                                Product.INTRODUCTION + "," +
                                Product.PRICE + "," +
                                Product.ORIGINAL_PRICE + "," +
                                Product.PRIORITY + "," +
                                Product.TAG );

        ItemCollection<ScanOutcome> itemCollection = productTable.scan(scanSpec);
        List<Product> products = mapScanOutcomeToProducts(itemCollection);

        Collections.sort(products,(p1, p2)-> {
            if (p1.getPriority() == p2.getPriority())
                return 0;

            if (p1.getPriority() == null) {
                return 1;
            }else if (p2.getPriority() == null) {
                return -1;
            }if (p1.getPriority() < p2.getPriority()){
                return 1;
            }else {
                return -1;
            }
        } );

        ProductKey productKey = getScanOutcomeLastKey(itemCollection);

        return Products.of(products, productKey);
    }


    @Override
    public Products getProductsByPrice(Integer price) {
        //todo
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public Products getProductsByProductName(String name) {
        checkPrimaryKey(name);

        QuerySpec spec = new QuerySpec()
                .withHashKey(Product.PRODUCT_NAME, name);

        ItemCollection<QueryOutcome> collection = productTable.query(spec);

        List<Product> products = mapQueryOutcomeToProducts(collection);

        ProductKey productKey = getQueryOutcomeLastKey(collection);

        return Products.of(products, productKey);
    }

    @Override
    public Product getProductByProductNameVersion(String productName, String version) {
        checkPrimaryKey(productName);
        checkPrimaryKey(version);

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
    public Integer getProductPrice(String productName, String version) {
        checkPrimaryKey(productName);
        checkPrimaryKey(version);

        QuerySpec querySpec = new QuerySpec()
                .withHashKey(Product.PRODUCT_NAME, productName)
                .withRangeKeyCondition(new RangeKeyCondition(Product.VERSION).eq(version));

        ItemCollection<QueryOutcome> collection = productTable.query(querySpec);

        List<Product> products = mapQueryOutcomeToProducts(collection);

        if (products.size() == 1) {
            Product product = products.get(0);
            return product.getPrice();
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

    @Override
    public boolean deleteProduct(String productName, String version) {
        checkPrimaryKey(productName);
        checkPrimaryKey(version);

        DeleteItemSpec spec = new DeleteItemSpec()
                .withPrimaryKey(Product.PRODUCT_NAME, productName, Product.VERSION, version);

        try {
            productTable.deleteItem(spec);
        }catch (ResourceNotFoundException e) {
            throw new ProductNotFoundException(productName, version);
        }

        return true;
    }

    private void checkPrimaryKey(String key) {
        if (key == null || key.length() == 0) {
            throw new ProductPrimaryKeyCannotEmptyException();
        }
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
        }catch (Exception e) {
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
