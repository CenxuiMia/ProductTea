package com.cenxui.tea.app.repositories.product;

import com.cenxui.tea.app.repositories.Repository;

public interface ProductRepository extends Repository {

    Products getAllProducts();

    Products getAllProductsProjectIntroSmallImagePriceTag();

    Products getProductsByTag(String tag);

    Products getProductsByPrice(Float price);

    Products getProductsByProductName(String productName);

    Product getProductByProductNameVersion(String productName, String version);

    Float getProductPrice(String name, String version);

    Product addProduct(Product product);

}
