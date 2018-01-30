package com.cenxui.shop.repositories.product;

import com.cenxui.shop.repositories.Repository;

import java.util.List;

public interface ProductBaseRepository extends Repository {

    Products getAllProducts();

    Products getAllSortedProductsPartial();

    Products getProductsByTag(String tag);

    Products getProductsByPrice(Integer price);

    Products getProductsByProductName(String productName);

    Product getProductByProductNameVersion(String productName, String version);

    Product addProduct(Product product);

    boolean deleteProduct(String productName, String version);

    Integer getProductPrice(String name, String version);
}
