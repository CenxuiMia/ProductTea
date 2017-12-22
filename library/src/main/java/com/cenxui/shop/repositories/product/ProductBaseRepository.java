package com.cenxui.shop.repositories.product;

import com.cenxui.shop.repositories.Repository;

import java.util.List;

public interface ProductBaseRepository extends Repository {

    Products getAllProducts();

    Products getAllProductsProjectIntroSmallImagePriceTag();

    Products getProductsByTag(String tag);

    Products getProductsByPrice(Integer price);

    Products getProductsByProductName(String productName);

    Product getProductByProductNameVersion(String productName, String version);

    Product addProduct(Product product);

    boolean deleteProduct(String productName, String version);

    Price getProductPrice(String name, String version);

    Price getProductsPrice(List<String> products);

}
