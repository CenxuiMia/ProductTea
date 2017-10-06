package com.cenxui.tea.app.service.catagory;

import com.cenxui.tea.app.service.catagory.repository.ProductRepositoryManager;
import com.cenxui.tea.app.service.core.CoreController;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class ProductController extends CoreController {

    public static Route fetchAllProducts = (Request request,  Response response) -> {
       List<Product> products = ProductRepositoryManager.getManager().getAllProducts();
       return products;
    };

    //TODO

}
