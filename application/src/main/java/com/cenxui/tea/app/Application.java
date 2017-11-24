package com.cenxui.tea.app;

import com.cenxui.tea.app.services.util.Header;
import com.cenxui.tea.app.services.order.OrderController;
import com.cenxui.tea.app.services.product.ProductController;
import com.cenxui.tea.app.services.util.Param;
import com.cenxui.tea.app.services.util.Path;

import static spark.Spark.*;

public final class Application {
    public static void main(String[] args) {

        //config
        port(9000);
        definceBasicResources();
        AuthResources();
        UnAuthResources();
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


    private static void UnAuthResources() {
        get(Path.Web.PRODUCT, ProductController.getAllProducts);
        get(Path.Web.PRODUCT + "/" + Param.NAME + "/" + Param.VERSION, ProductController.getProduct);





        get(Path.Web.INDEX, (req, rep) -> {
            return "Hello World";
        });
    }

    private static void AuthResources() {
        get("/users/:name/:version", ((request, response) -> {

            String name = request.params(":name");

            String version = request.params(":version");

            return "Hello " + name + version + ", Welcome!";
        }));




        get("/user", ((request, response) -> {

            return "user : " + request.headers(Header.MAIL) ;
        }));


        /**
         * todo add user post and get
         */



        /**
         * Order
         */
        get(Path.Web.ORDER, OrderController.getOrderByTMail);
        put(Path.Web.ORDER, OrderController.addOrder);

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
