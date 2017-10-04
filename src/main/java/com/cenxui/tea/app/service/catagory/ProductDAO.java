package com.cenxui.tea.app.service.catagory;

import java.util.*;

public final class ProductDAO {

    private final List<Product> products;

    public ProductDAO() {
        //TODO
        products = Collections.unmodifiableList(Arrays.asList(
                new Product(
                        "black tea" ,
                        "",
                        "",
                        "", new ArrayList<>(), Boolean.TRUE, 100),
                new Product(
                        "green tea" ,
                        "",
                        "",
                        "", new ArrayList<>(), Boolean.TRUE, 100)
        ));
    }


    public Iterator<Product> getAllProduct() {
        return products.iterator();
    }


}
