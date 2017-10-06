package com.cenxui.tea.app.service.catagory.repository;

import com.cenxui.tea.app.service.catagory.Product;
import com.cenxui.tea.app.service.core.Repository;

import java.util.List;

public interface ProductRepository extends Repository {

    Product getProductById(Integer id);

    List<Product> getAllProducts();

    List<Product> getProductsByTag(String tag);

    List<Product> getProductsByPrice(Integer price);

    List<Product> getProductsByName(String name);
}
