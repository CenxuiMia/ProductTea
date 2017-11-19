package com.cenxui.tea.app.aws.dynamodb.repositories;

import org.junit.Test;

import static org.junit.Assert.*;

public class DynamoDBProductRepositoryTest {
    @Test
    public void getProductsByTag() throws Exception {
    }

    @Test
    public void getAllProducts() throws Exception {
        System.out.println(DynamoDBRepositoryService.getProductRepository().getAllProducts());
    }

    @Test
    public void getProductsByPrice() throws Exception {
    }

    @Test
    public void getProductsByName() throws Exception {
    }

    @Test
    public void getProductByNameVersion() throws Exception {
    }

    @Test
    public void getAllProductsJSON() throws Exception {
    }

}