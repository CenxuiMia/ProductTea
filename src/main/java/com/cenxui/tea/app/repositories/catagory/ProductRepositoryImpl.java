package com.cenxui.tea.app.repositories.catagory;

import java.util.*;
import java.util.stream.Collectors;

public final class ProductRepositoryImpl implements ProductRepository {

    private final List<Product> products;

    //cache all products
    private static final ProductRepositoryImpl manager = new ProductRepositoryImpl();

    public static ProductRepositoryImpl getManager() {
        return manager;
    }

    private ProductRepositoryImpl() {
        //TODO please implement database here
        products = Collections.unmodifiableList(Arrays.asList(
                Product.of(
                        "black tea" ,
                        1,
                        "good tea from mia banana",
                        "",
                        "", new ArrayList<>(), Boolean.TRUE, 100.0, "mia"),
                Product.of(
                        "green tea" ,
                        1,
                        "standard tea from cenxui banana",
                        "",
                        "",
                        new ArrayList<>(), Boolean.TRUE, 100.0, "cenxui"),
                Product.of(
                        "woolong tea",
                        1,
                        "woolong tea from cenxui mia",
                        "",
                        "",
                        new ArrayList<>(), Boolean.TRUE, 200.0, "cenxui"),
                Product.of(
                        "mountain green tea" ,
                        1,
                        "mountain tea from cenxui mia",
                        "",
                        "",
                        new ArrayList<>(), Boolean.TRUE, 200.0, "mia")
        ));
    }

    @Override
    public List<Product> getProductsByTag(String tag) {
        return products.stream().filter(product -> product.getTag().equals(tag)).collect(Collectors.toList());
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

    @Override
    public Product getProductByNameVersion(String name, String version) {
        return products.stream()
                .filter(product -> product.getName().equals(name) && product.getVersion().equals(version))
                .findAny()
                .get();
    }

}
