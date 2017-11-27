package com.cenxui.tea.app.services.admin.product;

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

public class AdminProductController extends CoreController {

    private static final ProductRepository productRepository =
            DynamoDBRepositoryService.getProductRepository(
                    DynamoDBConfig.REGION,
                    DynamoDBConfig.PRODUCT_TABLE
            );

    public static final Route addProduct = (Request request, Response response) -> {
        Product product = JsonUtil.mapToProduct(request.body());
        Product productResult = productRepository.addProduct(product);

        return JsonUtil.mapToJson(productResult);
    };

    public static final Route getAllProducts = (Request request, Response response) -> {
        return JsonUtil.mapToJson(productRepository.getAllProducts());
    };

    public static final Route getProduct = (Request request, Response response) -> {
        Map<String, String> map = request.params();
        String productName = getProductName(map);
        String productVersion = getProductVersion(map);
        return JsonUtil.mapToJson(productRepository.getProductByProductNameVersion(productName, productVersion));
    };


    public static final Route updateProduct = (Request request, Response response) -> {
        //todo
        throw new UnsupportedOperationException("not yet");
    };

    public static final Route deleteProduct = (Request request, Response response) -> {
        //todo
        throw new UnsupportedOperationException("not yet");
    };

    private static String getProductName(Map<String, String> map) {
        return map.get(Param.PRODUCT_NAME);
    }

    private static String getProductVersion(Map<String, String> map) {
        return map.get(Param.PRODUCT_VERSION);
    }
}
