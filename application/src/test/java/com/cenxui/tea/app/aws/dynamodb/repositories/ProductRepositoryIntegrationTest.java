package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.cenxui.tea.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ProductRepositoryIntegrationTest {
    private Table table;

    @Before
    public void setUp() {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
                .standard()
                .withRegion(Regions.AP_NORTHEAST_1).build(); // in product state do not input any credential
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        table = dynamoDB.getTable(DynamoDBConfig.PRODUCT_TABLE);
    }

    @Test
    public void dataLifecycle() {
        putItems();
        queryItems();
    }

    private void queryItems() {
//        List<Product> products = new ArrayList<>();
//        table.scan().forEach(
//                (s) -> {
//                    ObjectMapper objectMapper = new ObjectMapper();
//                    try {
//                        Product product = objectMapper.readValue(s.toJSON(), ItemProduct.class).getItem();
//                        products.add(product);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//        );
//
//        products.stream().forEach(System.out::println);
    }

    private void putItems() {

//        List<String> images = Arrays.asList("a", "b", "c");
//
//        List<Product> products = Collections.unmodifiableList(Arrays.asList(
//                Product.of(
//                        "black tea",
//                        "1",
//                        "good tea from mia banana",
//                        "sm",
//                        "bm",
//                        images, Boolean.TRUE, 100.0, "mia"),
//                Product.of(
//                        "green tea",
//                        "1",
//                        "standard tea from cenxui banana",
//                        "sm",
//                        "bm",
//                        images, Boolean.TRUE, 100.0, "cenxui"),
//                Product.of(
//                        "woolong tea",
//                        "1",
//                        "woolong tea from cenxui mia",
//                        "sm",
//                        "bm",
//                        images, Boolean.TRUE, 200.0, "cenxui"),
//                Product.of(
//                        "mountain green tea",
//                        "1",
//                        "mountain tea from cenxui mia",
//                        "sm",
//                        "bm",
////                        images, Boolean.TRUE, 200.0, "mia")
//        ));
//
//        for (Product product : products) {
//
//            table.putItem(ItemUtil.getProductItem(product));
//        }
    }
}
