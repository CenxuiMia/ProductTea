package com.cenxui.tea.app;

import com.cenxui.tea.app.services.admin.order.AdminOrderController;
import com.cenxui.tea.app.services.admin.product.AdminProductController;
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
        authResources();
        unAuthResources();
        adminResources();
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


    private static void unAuthResources() {
        get(Path.Web.PRODUCT, ProductController.getAllProducts);
        get(Path.Web.PRODUCT + "/" + Param.PRODUCT_NAME + "/" + Param.PRODUCT_VERSION, ProductController.getProduct);

        get(Path.Web.INDEX, (req, rep) -> {
            return "Hello World";
        });
    }

    private static void authResources() {

        get("/user", ((request, response) -> {

            return "user : " + request.headers(Header.MAIL) ;
        }));


        /**
         * todo add user post and get
         */
        /**
         * Order
         */
        get(Path.Web.ORDER, OrderController.getOrdersByMail);
        put(Path.Web.ORDER, OrderController.addOrder);

    }

    private static void adminResources() {
        /**
         * orders
         */

        get(Path.Web.Admin.ORDER, AdminOrderController.getAllOrders);

        get(Path.Web.Admin.ORDER + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_TIME + "/" + Param.ORDER_COUNT,
                AdminOrderController.getOrdersByLastKey);

        get(Path.Web.Admin.ORDER + "/" + Param.ORDER_MAIL, AdminOrderController.getOrdersByMail);


        //todo paid processing shipped


        /**
         * order
         */

        get(Path.Web.Admin.ORDER + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_TIME,
                (request, response) -> {

                    return  "get order" + request.params(Param.ORDER_MAIL) + request.params(Param.ORDER_TIME) ;
                });


        put(Path.Web.Admin.ORDER + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_TIME,(request, response) -> {

            return  "add order" + request.params(Param.ORDER_MAIL) + request.params(Param.ORDER_TIME) ;
        });

        post(Path.Web.Admin.ORDER + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_TIME,(request, response) -> {

            return  "update order" + request.params(Param.ORDER_MAIL) + request.params(Param.ORDER_TIME) ;
        });


        /**
         * products
         */

        get(Path.Web.Admin.PRODUCT,  AdminProductController.getAllProducts);

        /**
         * product
         */

        get(Path.Web.Admin.PRODUCT, AdminProductController.getAllProducts);

        get(Path.Web.Admin.PRODUCT + "/" + Param.PRODUCT_NAME + "/" + Param.PRODUCT_VERSION,
                ProductController.getProduct);

        put(Path.Web.Admin.PRODUCT, AdminProductController.addProduct);

        post(Path.Web.Admin.PRODUCT, (request, response) -> {

            return  "update product" + request.params(Param.PRODUCT_NAME) + request.params(Param.PRODUCT_VERSION);
        });


        /**
         * user
         */

        get(Path.Web.Admin.USER, (request, response) -> {

            return  "get all user";
        });

        get(Path.Web.Admin.USER + "/" + Param.ORDER_MAIL,  (request, response) -> {

            return  "get user";
        });




    }

    /**
     * add unauth route here
     */

    public static void defineUnAuthResources() {
        unAuthResources();
        definceBasicResources();
    }

    /**
     * add auth route here
     */

    public static void defineAuthResources() {
        authResources();
        definceBasicResources();
    }

    public static void defineAdminResources() {
        adminResources();
        definceBasicResources();
    }

}
