package com.cenxui.tea.app.services.product;

import com.cenxui.tea.app.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.services.CoreController;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class ProductController extends CoreController {

    public static Route fetchAllProducts = (Request request,  Response response) -> {
        return getAllProducts();
    };

    public static List<Product> getAllProducts() {
        List<Product> products = DynamoDBRepositoryService.getProductRepository().getAllProducts();
        return products;
    }

    //TODO

}
