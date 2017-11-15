package com.cenxui.tea.app;

import com.cenxui.tea.app.aws.lambda.handlers.AuthLambdaHandler;
import com.cenxui.tea.app.services.order.OrderController;
import com.cenxui.tea.app.services.product.ProductController;
import com.cenxui.tea.app.services.util.Path;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public final class Application {
    public static void main(String[] args) {

        //config
        port(9000);
        definceBasicResources();
        AuthResources();
        UnAuthResources();
    }

    /**
     * add same route here
     */

    private static void definceBasicResources() {
        before(((request, response) -> {
            /**
             * todo modify to https://tw.hwangying,com
             */
            response.header("Access-Control-Allow-Origin", "*");
        }));

        /**
         * remove it after test
         */

        options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

    }


    private static void UnAuthResources() {
        get(Path.Web.PRODUCT, ProductController.getAllProducts);
        get(Path.Web.INDEX, (req, rep) -> {
            return "Hello World";
        });
    }

    private static void AuthResources() {
        get("/users/:name", ((request, response) -> {

            String name = request.params(":name");

            return "Hello " + name + ", Welcome!";
        }));


        get(Path.Web.ORDER, OrderController.getOrderByTMail);

        get("/user", ((request, response) -> {
            Map<String, String> userdata = new HashMap<>();

            for (String header : request.headers()) {
                userdata.put(header, request.headers(header));
            }

            return "user : " + userdata.get(AuthLambdaHandler.MAIL) ;
        }));

        put(Path.Web.ORDER, ((OrderController.addOrder)));

    }


    /**
     * add unauth route here
     */

    public static void defineUnAuthResources() {
        UnAuthResources();
        definceBasicResources();
    }

    /**
     * add auth route here
     */

    public static void defineAuthResources() {
        AuthResources();
        definceBasicResources();
    }

}
