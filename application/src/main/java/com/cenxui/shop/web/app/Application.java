package com.cenxui.shop.web.app;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.cenxui.shop.web.app.controller.ControllerClientException;
import com.cenxui.shop.web.app.controller.order.OrderController;
import com.cenxui.shop.web.app.controller.product.ProductController;
import com.cenxui.shop.web.app.controller.user.UserController;
import com.cenxui.shop.web.app.controller.util.Param;
import com.cenxui.shop.web.app.controller.util.Path;
import com.cenxui.shop.aws.dynamodb.exceptions.client.RepositoryClientException;

import static spark.Spark.*;

public final class Application {
    public static void main(String[] args) {

        //config
        port(9001);
        defineBasicResources();
        authResources();
        unAuthResources();
        options("/*",
                (request, response) -> {

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }


                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    return "OK";
                });

        exception(AmazonServiceException.class, (exception, request, response) -> {
            response.body("retry, error :" + exception.getMessage());
            response.status(501);
        });

        exception(AmazonClientException.class, (exception, request, response) -> {
            response.body(exception.getMessage());
            response.status(502);
        });


    }

    /**
     * add same route here
     */

    private static void defineBasicResources() {
        before(((request, response) -> {
            /**
             * todo modify to https://tw.hwangying,com
             */
            response.header("Access-Control-Allow-Origin", "*");
        }));

        exception(RepositoryClientException.class, (exception, request, response) -> {
            response.body(exception.getMessage());
            response.status(400);
        });

        exception(ControllerClientException.class, (exception, request, response) -> {
            response.body(exception.getMessage());
            response.status(400);
        });
    }


    private static void unAuthResources() {
        get(Path.Web.PRODUCT, ProductController.getAllProducts);
        get(Path.Web.PRODUCT + "/" + Param.PRODUCT_NAME + "/" + Param.PRODUCT_VERSION,
                ProductController.getProduct);
    }

    private static void authResources() {
        /**
         * user
         */
        get(Path.Web.USER, UserController.getUserProfile);
        post(Path.Web.USER, UserController.updateUserProfile);

        /**
         * Order
         */
        get(Path.Web.ORDER, OrderController.getOrdersByMail);
        put(Path.Web.ORDER, OrderController.addOrder);
        post(Path.Web.ORDER, OrderController.trialOrder);
        delete(Path.Web.ORDER + "/" + Param.ORDER_DATE_TIME, OrderController.deActiveOrder);
    }

    /**
     * add unauth route here
     */

    public static void defineUnAuthResources() {
        unAuthResources();
        defineBasicResources();
    }

    /**
     * add auth route here
     */

    public static void defineAuthResources() {
        authResources();
        defineBasicResources();
    }

}
