package com.cenxui.tea.app.service.catagory;

import java.util.*;

public final class ProductDAO {

    private final List<Product> products;

    public ProductDAO() {
        //TODO
        products = Collections.unmodifiableList(Arrays.asList(
                new Product(
                        1,
                        "black tea" ,
                        "good tea from mia banana",
                        "",
                        "", new ArrayList<>(), Boolean.TRUE, 100, "mia"),
                new Product(
                        2,
                        "green tea" ,
                        "standard tea from cenxui",
                        "",
                        "",
                        new ArrayList<>(), Boolean.TRUE, 100, "cenxui")
        ));
    }

    public Product getProductByTag(String tag) {
        //TODO
        return null;
    }

    public Product getProductById(Integer id) {
        //TODO
        return null;
    }


    public Iterator<Product> getAllProducts() {
        return products.iterator();
    }


    public Iterator<Product> getProductsByPrice(Integer price) {
        //TODO
        return null;
    }

    public Product getProductByName(String name) {
        //TODO
        return null;
    }

    public Iterator<Product> getProductsByPrefix(String prefix) {
        //TODO
        return null;
    }

}
