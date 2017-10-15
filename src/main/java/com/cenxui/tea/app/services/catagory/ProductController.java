package com.cenxui.tea.app.services.catagory;

import com.cenxui.tea.app.repositories.catagory.Product;
import com.cenxui.tea.app.repositories.catagory.ProductRepositoryImpl;
import com.cenxui.tea.app.services.CoreController;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class ProductController extends CoreController {

    public static Route fetchAllProducts = (Request request,  Response response) -> {
       List<Product> products = ProductRepositoryImpl.getManager().getAllProducts();
       return products;
    };

    //TODO

}
