package com.cenxui.tea.app.service.catagory;

import java.util.*;
import java.util.stream.Collectors;

public final class ProductDAO {

    private final List<Product> products;

    //cache all products
    private static final ProductDAO productDAO = new ProductDAO();

    //define if cached or not
    public static ProductDAO getProductDAO() {
        //TODO
        return productDAO;
    }

    //for unit test
    static ProductDAO getTestProductDAO(List<Product> products) {
        return new ProductDAO(products);
    }


    //for unit test
    ProductDAO(List<Product> products) {
        this.products = products;
    }


    public ProductDAO() {
        //TODO

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

    public Product getProductByTag(String tag) {
        return products.stream().filter(product -> product.getTag().equals(tag)).findFirst().orElse(null);
    }

    public Product getProductById(Integer id) {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }


    public Iterable<Product> getAllProducts() {
        return products;
    }


    public Iterable<Product> getProductsByPrice(Integer price) {
        return products.stream().filter(product -> product.getPrice().equals(price)).collect(Collectors.toList());
    }

    public Product getProductByName(String name) {
        return products.stream().filter(product -> product.getName().equals(name)).findFirst().orElse(null);
    }

}
