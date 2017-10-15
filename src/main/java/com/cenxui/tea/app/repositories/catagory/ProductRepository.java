package com.cenxui.tea.app.repositories.catagory;

import com.cenxui.tea.app.repositories.Repository;

import java.util.List;

public interface ProductRepository extends Repository {

    Product getProductById(Integer id);

    List<Product> getAllProducts();

    List<Product> getProductsByTag(String tag);

    List<Product> getProductsByPrice(Integer price);

    List<Product> getProductsByName(String name);
}
