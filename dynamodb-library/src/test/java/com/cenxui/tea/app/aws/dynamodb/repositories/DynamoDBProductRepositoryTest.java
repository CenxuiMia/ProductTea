package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.product.ProductRepository;
import lombok.NonNull;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class DynamoDBProductRepositoryTest {
    private ProductRepository productRepository;

    @Before
    public void setUp() {
       productRepository =
                DynamoDBRepositoryService.getProductRepository(
                        Config.REGION,
                        Config.PRODUCT_TABLE
                );
    }

    @Test
    public void addProducts() {

        Product product = productRepository.addProduct(Product.of("綠茶", "翠玉冰茶", "Ａ","Ａ","Ａ","A",
                new ArrayList<>(),40000F,"Ａ"));

        System.out.println(product);

    }

    @Test
    public void getAllProducts() throws Exception {
        productRepository.getAllProducts().getProducts().forEach(System.out::println);

    }

    @Test
    public void testMap() {
        Map<String, Product> map = new TreeMap<>();
        map.put("1", null);
        System.out.println(map.get("1"));
    }



    @Test
    public void getAllProductsForShort() throws Exception {
        productRepository.getAllProductsProjectIntroSmallImagePriceTag()
                .getProducts().forEach(System.out::println);
    }

    @Test
    public void getProductByNameVersion() {
        System.out.println(productRepository.getProductByProductNameVersion("綠茶","翠玉"));
    }

    @Test
    public void getProductPrice() throws Exception {

    }

}