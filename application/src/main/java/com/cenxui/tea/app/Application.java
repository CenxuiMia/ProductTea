package com.cenxui.tea.app;

import com.cenxui.tea.app.aws.dynamodb.exceptions.map.order.OrderNotFoundException;
import com.cenxui.tea.app.aws.dynamodb.exceptions.map.product.ProductNotFoundException;
import com.cenxui.tea.app.aws.dynamodb.exceptions.map.user.UserNotFoundException;
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
        defineBasicResources();
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

    private static void defineBasicResources() {
        before(((request, response) -> {
            /**
             * todo modify to https://tw.hwangying,com
             */
            response.header("Access-Control-Allow-Origin", "*");
        }));
//
//        exception(OrderNotFoundException.class, (exception, request, response) -> {
//            response.body(exception.getMessage());
//        });
//
//        exception(ProductNotFoundException.class, (exception, request, response) -> {
//            response.body(exception.getMessage());
//        });
//
//        exception(UserNotFoundException.class, (exception, request, response) -> {
//            response.body(exception.getMessage());
//        });
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
                AdminOrderController.getOrderByMailAndTime);

        /**
         * add order
         */
        put(Path.Web.Admin.ORDER + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_TIME,
                AdminOrderController.addOrder);


        /**
         * products
         */

        get(Path.Web.Admin.PRODUCT,  AdminProductController.getAllProducts);



        /**
         * product
         */

        get(Path.Web.Admin.PRODUCT + "/" + Param.PRODUCT_NAME + "/" + Param.PRODUCT_VERSION,
                AdminProductController.getProduct);

        /**
         * add product
         */
        put(Path.Web.Admin.PRODUCT, AdminProductController.addProduct);

        post(Path.Web.Admin.PRODUCT, AdminProductController.updateProduct);


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
        defineBasicResources();
    }

    /**
     * add auth route here
     */

    public static void defineAuthResources() {
        authResources();
        defineBasicResources();
    }

    public static void defineAdminResources() {
        adminResources();
        defineBasicResources();
    }

}
