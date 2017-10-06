package com.cenxui.tea.app.service.catagory.repository;

import com.cenxui.tea.app.service.catagory.Product;
import com.cenxui.tea.app.service.core.Repository;

import java.util.List;

public interface ProductRepository extends Repository {
    List<Product> getProductsByTag(String tag);

    Product getProductById(Integer id);

    List<Product> getAllProducts();

    List<Product> getProductsByPrice(Integer price);

    Product getProductByName(String name);
}
