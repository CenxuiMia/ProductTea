package com.cenxui.tea.app.services.product;

import com.cenxui.tea.app.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.tea.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.product.ProductRepository;
import com.cenxui.tea.app.services.CoreController;
import com.cenxui.tea.app.util.JsonUtil;
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

    private static String productJson;

    public static final Route getAllProducts = (Request request,  Response response) -> {
        if (productJson == null) {
            String head = "{\"products\" : " ;
            String products = JsonUtil.mapToJson(productRepository.getAllProducts());
            String tail = "}";

            productJson = new StringBuilder().append(head).append(products).append(tail).toString();
        }




        return productJson;
    };


    //TODO

}
