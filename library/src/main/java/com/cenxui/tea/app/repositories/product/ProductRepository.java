package com.cenxui.tea.app.repositories.product;

import com.cenxui.tea.app.repositories.Repository;

import java.util.List;

public interface ProductRepository extends Repository {

    Products getAllProducts();

    Products getAllProductsProjectIntroSmallImagePriceTag();

    Products getProductsByTag(String tag);

    Products getProductsByPrice(Float price);

    Products getProductsByProductName(String productName);

    Product getProductByProductNameVersion(String productName, String version);

    Product addProduct(Product product);

    boolean deleteProduct(String productName, String version);

    Price getProductPrice(String name, String version);

    Price getProductsPrice(List<String> products);

}
