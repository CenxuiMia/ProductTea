package com.cenxui.tea.app.services.product;

import com.cenxui.tea.app.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.tea.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.product.ProductRepository;
import com.cenxui.tea.app.services.CoreController;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * return products value in json
 *  {
 *      "products" : []
 *  }
 *
 */

/**
 * todo add cache
 */

public class ProductController extends CoreController {

    private static final ProductRepository productRepository =
            DynamoDBRepositoryService.getProductRepository(
                    DynamoDBConfig.REGION,
                    DynamoDBConfig.PRODUCT_TABLE
            );

    public static final Route getAllProducts = (Request request,  Response response) -> {
//        String head = "{\"products\" : " ;
//        String products = productRepository.getAllProductsJSON();
//        String tail = "}";
//
//        StringBuilder json = new StringBuilder();
//        json.append(head).append(products).append(tail);

        return "not yet";
    };


    //TODO

}
