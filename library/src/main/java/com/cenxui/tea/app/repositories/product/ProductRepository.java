package com.cenxui.tea.app.repositories.product;

import com.cenxui.tea.app.repositories.Repository;

import java.util.List;

public interface ProductRepository extends Repository {

    ProductResult getAllProducts();

    ProductResult getAllProductsProjectIntroSmallImagePriceTag();

    ProductResult getProductsByTag(String tag);

    ProductResult getProductsByPrice(Float price);

    ProductResult getProductsByProductName(String productName);

    Product getProductByProductNameVersion(String productName, String version);

    Float getProductPrice(String name, String version);

    boolean addProduct(Product product);
}
