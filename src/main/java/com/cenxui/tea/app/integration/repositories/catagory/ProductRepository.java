package com.cenxui.tea.app.integration.repositories.catagory;

import com.cenxui.tea.app.integration.repositories.Repository;

import java.util.List;

public interface ProductRepository extends Repository {

    List<Product> getAllProducts();

    List<Product> getProductsByTag(String tag);

    List<Product> getProductsByPrice(Integer price);

    List<Product> getProductsByName(String name);

    Product getProductByNameVersion(String name, String version);
}
