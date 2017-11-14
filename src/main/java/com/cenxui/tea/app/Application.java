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
        defineUnAuthResources();
        defineAuthResources();
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

    }

    /**
     * add unauth route here
     */

    public static void defineUnAuthResources() {
        get(Path.Web.PRODUCT, ProductController.getAllProducts);
        get(Path.Web.INDEX, (req, rep) -> {
            return "Hello World";
        });

        definceBasicResources();
    }

    /**
     * add auth route here
     */

    public static void defineAuthResources() {

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


        definceBasicResources();

    }

}
