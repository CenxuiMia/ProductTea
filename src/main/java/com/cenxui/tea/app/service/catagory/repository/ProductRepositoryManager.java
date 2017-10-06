package com.cenxui.tea.app.service.catagory.repository;

import com.cenxui.tea.app.service.catagory.Product;

import java.util.*;
import java.util.stream.Collectors;

public final class ProductRepositoryManager implements ProductRepository {

    private final List<Product> products;

    //cache all products
    private static final ProductRepositoryManager manager = new ProductRepositoryManager();

    public static ProductRepositoryManager getManager() {
        return manager;
    }

    private ProductRepositoryManager() {
        //TODO please implement database here
        products = Collections.unmodifiableList(Arrays.asList(
                Product.of(
                        1,
                        "black tea" ,
                        "good tea from mia banana",
                        "",
                        "", new ArrayList<>(), Boolean.TRUE, 100, "mia"),
                Product.of(
                        2,
                        "green tea" ,
                        "standard tea from cenxui banana",
                        "",
                        "",
                        new ArrayList<>(), Boolean.TRUE, 100, "cenxui"),
                Product.of(
                        3,
                        "woolong tea" ,
                        "woolong tea from cenxui mia",
                        "",
                        "",
                        new ArrayList<>(), Boolean.TRUE, 200, "cenxui"),
                Product.of(
                        4,
                        "mountain green tea" ,
                        "mountain tea from cenxui mia",
                        "",
                        "",
                        new ArrayList<>(), Boolean.TRUE, 200, "mia")
        ));
    }

    @Override
    public List<Product> getProductsByTag(String tag) {
        return products.stream().filter(product -> product.getTag().equals(tag)).collect(Collectors.toList());
    }

    @Override
    public Product getProductById(Integer id) {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public List<Product> getProductsByPrice(Integer price) {
        return products.stream().filter(product -> product.getPrice().equals(price)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return products.stream().filter(product -> product.getName().equals(name)).collect(Collectors.toList());
    }

}
