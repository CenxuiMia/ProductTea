package com.cenxui.tea.app.services.product;

import com.cenxui.tea.app.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.services.CoreController;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Collections;
import java.util.List;

/**
 * return products value in json
 *  {
 *      "products" : []
 *  }
 *
 */


public class ProductController extends CoreController {

    public static Route getAllProducts = (Request request,  Response response) -> {
        String head = "{\"products\" : " ;
        String products = DynamoDBRepositoryService.getProductRepository().getAllProductsJSON();
        String tail = "}";

        StringBuilder json = new StringBuilder();
        json.append(head).append(products).append(tail);

        return json.toString();
    };


    //TODO

}
