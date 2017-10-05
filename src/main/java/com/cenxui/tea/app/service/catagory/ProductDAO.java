package com.cenxui.tea.app.service.catagory;

import java.util.*;

public final class ProductDAO {

    private final List<Product> products;

    //cache all products
    private static final ProductDAO productDAO = new ProductDAO();

    //define if cached or not
    public static ProductDAO getProductDAO() {
        //TODO
        return productDAO;
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


    public List<Product> getAllProducts() {
        return products;
    }


    public List<Product> getProductsByPrice(Integer price) {
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
