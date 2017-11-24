package com.cenxui.tea.app.repositories.product;

import com.cenxui.tea.app.repositories.Repository;

import java.util.List;

public interface ProductRepository extends Repository {

    ProductResult getAllProducts();

    ProductResult getProductsByTag(String tag);

    ProductResult getProductsByPrice(Float price);

    ProductResult getProductsByName(String name);

    Product getProductByNameVersion(String name, String version);

    Float getProductPrice(String name, String version);

    boolean addProduct(Product product);
}
