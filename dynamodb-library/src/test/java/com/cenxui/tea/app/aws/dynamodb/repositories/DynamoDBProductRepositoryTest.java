package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.product.ProductRepository;
import org.junit.Before;
import org.junit.Test;

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
    public void getAllProducts() throws Exception {
        productRepository.getAllProducts().forEach(System.out::println);

    }

    @Test
    public void getProductPrice() throws Exception {
        System.out.println("price : " + productRepository.getProductPrice("green tea", "1"));
        System.out.println("price : " + productRepository.getProductPrice("black tea", "1"));
    }

}