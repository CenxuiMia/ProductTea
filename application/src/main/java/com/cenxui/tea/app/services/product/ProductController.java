package com.cenxui.tea.app.services.product;

import com.cenxui.tea.app.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.tea.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.product.Product;
import com.cenxui.tea.app.repositories.product.ProductRepository;
import com.cenxui.tea.app.repositories.product.ProductResult;
import com.cenxui.tea.app.services.CoreController;
import com.cenxui.tea.app.services.util.Param;
import com.cenxui.tea.app.util.JsonUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    private static Map<String,Map<String,Product>> productMap;
    private static String productJson;

    static {
        ProductResult productResult = productRepository.getAllProducts();
        productJson = JsonUtil.mapToJson(productResult);


        productMap = new TreeMap<>();
        List<Product> products = productResult.getProducts();
        for (Product product : products) {
            if (productMap.containsKey(product.getName()) == false) {
                productMap.put(product.getName(),new TreeMap<>());
            }
            productMap.get(product.getName()).put(product.getVersion(), product);
        }
    }

    public static final Route getAllProducts = (Request request,  Response response) -> {

        return productJson;
    };

    public static final Route getProduct = (Request request,  Response response) -> {

        Product product = null;
        try {
            String name = request.params(Param.NAME);
            String version = request.params(Param.VERSION);

            product = productMap.get(name).get(version);
        }catch (Throwable e) {
            StringBuilder m = new StringBuilder();
            m.append(e.getMessage());
            for (StackTraceElement s : e.getStackTrace()) {
                m.append(s);
            }
            return m;
        }


        return JsonUtil.mapToJson(product);
    };

    //todo

}
