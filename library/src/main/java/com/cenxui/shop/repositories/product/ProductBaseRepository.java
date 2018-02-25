package com.cenxui.shop.repositories.product;

import com.cenxui.shop.repositories.Repository;

import java.util.List;

public interface ProductBaseRepository extends Repository {

    Products getAllProducts();

    Products getAllSortedProductsPartial();

    Products getProductsByProductName(String productName);

    Product getProductByProductNameVersion(String productName, String version);

    Product subtractProductAmount(String productName, String version , Integer amount);

    Product addProduct(Product product);

    boolean deleteProduct(String productName, String version);
}
