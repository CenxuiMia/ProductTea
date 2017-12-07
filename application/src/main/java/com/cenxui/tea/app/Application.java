package com.cenxui.tea.app;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;
import com.cenxui.tea.app.services.ControllerException;
import com.cenxui.tea.app.services.user.UserController;
import com.cenxui.tea.app.services.order.OrderController;
import com.cenxui.tea.app.services.product.ProductController;
import com.cenxui.tea.app.services.util.Param;
import com.cenxui.tea.app.services.util.Path;

import static spark.Spark.*;

public final class Application {
    public static void main(String[] args) {

        //config
        port(9000);
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

        exception(RepositoryException.class, (exception, request, response) -> {
            response.body(exception.getMessage());
            response.status(400);
        });

        exception(ControllerException.class, (exception, request, response) -> {
            response.body(exception.getMessage());
            response.status(400);
        });

        //todo dynaomdb Server and Client exception

    }


    private static void unAuthResources() {
        get(Path.Web.PRODUCT, ProductController.getAllProducts);
        get(Path.Web.PRODUCT + "/" + Param.PRODUCT_NAME + "/" + Param.PRODUCT_VERSION,
                ProductController.getProduct);

        get(Path.Web.INDEX, (req, rep) -> {
            return "Hello World";
        });
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
