package com.cenxui.tea.app.repositories.product;

import com.cenxui.tea.app.repositories.Repository;

import java.util.List;

public interface ProductRepository extends Repository {

    List<Product> getAllProducts();

    List<Product> getProductsByTag(String tag);

    List<Product> getProductsByPrice(Integer price);

    List<Product> getProductsByName(String name);

    Product getProductByNameVersion(String name, String version);

    String getAllProductsJSON();
}
