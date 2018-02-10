package com.cenxui.shop.aws.dynamodb.repositories;

import com.cenxui.shop.repositories.product.Product;
import com.cenxui.shop.repositories.product.ProductRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

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
    public void deleteProduct() {
        productRepository.deleteProduct("美茶", "美人茶" );   }

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

    }

    @Test
    public void getProductByNameVersion() {
        System.out.println(productRepository.getProductByProductNameVersion("綠茶","翠玉"));
    }

    @Test
    public void getProductPrice() throws Exception {

    }

}