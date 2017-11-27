package com.cenxui.tea.app.services.product;

import com.cenxui.tea.app.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.tea.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.product.ProductRepository;
import com.cenxui.tea.app.services.CoreController;
import com.cenxui.tea.app.services.util.Param;
import com.cenxui.tea.app.util.JsonUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;
import java.util.TreeMap;

/**
 * todo add cache
 */

public class ProductController extends CoreController {

    private static final ProductRepository productRepository =
            DynamoDBRepositoryService.getProductRepository(
                    DynamoDBConfig.REGION,
                    DynamoDBConfig.PRODUCT_TABLE
            );

    private static final Map<String,Map<String,Product>> productMap = new TreeMap<>();
    private static String productJson;

    public static final Route getAllProducts = (Request request,  Response response) -> {
        if (productJson == null) {
            productJson = JsonUtil.mapToJsonIgnoreNull(
                    productRepository.getAllProductsProjectIntroSmallImagePriceTag());
        }

        return productJson;
    };

    public static final Route getProduct = (Request request,  Response response) -> {
        String productName = request.params(Param.PRODUCT_NAME);
        String version = request.params(Param.VERSION);

        if (productMap.containsKey(productName) == false) {
            productMap.put(productName, new TreeMap<>());
        }

        if (productMap.get(productName).containsKey(version) == false) {
            Product product =
                    productRepository.getProductByProductNameVersion(productName, version);

            productMap.get(productName).put(version, product);
        }

        return JsonUtil.mapToJsonIgnoreNull(productMap.get(productName).get(version));
    };

    public static final Route addProduct = (Request request, Response response) -> {
        Product product = JsonUtil.mapToProduct(request.body());

        boolean isSuccess = productRepository.addProduct(product);

      return isSuccess ? "success" : "fail";
    };
    //todo

}
