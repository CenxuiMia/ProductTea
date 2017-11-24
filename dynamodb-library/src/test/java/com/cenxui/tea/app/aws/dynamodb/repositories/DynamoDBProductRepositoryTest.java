package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.product.ProductRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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

//        productRepository.addProduct(Product.of("綠茶", "翠玉", "Ａ","Ａ","Ａ",
//                new ArrayList<>(),true,500F,"Ａ"));
//
//        productRepository.addProduct(Product.of("紅茶", "阿里山", "Ａ","Ａ","Ａ",
//                new ArrayList<>(),true,500F,"Ａ"));
//
//        productRepository.addProduct(Product.of("紅茶", "大禹嶺", "Ａ","Ａ","Ａ",
//                new ArrayList<>(),true,500F,"Ａ"));
        productRepository.addProduct(Product.of("greentea", "1", "Ａ","Ａ","Ａ",
                new ArrayList<>(),true,500F,"Ａ"));

    }

    @Test
    public void getAllProducts() throws Exception {
        productRepository.getAllProducts().getProducts().forEach(System.out::println);

    }

    @Test
    public void getProductPrice() throws Exception {
        System.out.println("price : " + productRepository.getProductPrice("green tea", "1"));
        System.out.println("price : " + productRepository.getProductPrice("black tea", "1"));
    }

}