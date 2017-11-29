package com.cenxui.tea.app;

import com.cenxui.tea.app.aws.dynamodb.exceptions.map.order.OrderNotFoundException;
import com.cenxui.tea.app.aws.dynamodb.exceptions.map.product.ProductNotFoundException;
import com.cenxui.tea.app.aws.dynamodb.exceptions.map.user.UserNotFoundException;
import com.cenxui.tea.app.services.admin.order.AdminOrderController;
import com.cenxui.tea.app.services.admin.product.AdminProductController;
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

        exception(OrderNotFoundException.class, (exception, request, response) -> {
            response.body(exception.getMessage());
        });

        exception(ProductNotFoundException.class, (exception, request, response) -> {
            response.body(exception.getMessage());
        });

        exception(UserNotFoundException.class, (exception, request, response) -> {
            response.body(exception.getMessage());
        });
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

        get(Path.Web.USER, UserController.getUserProfile);
        post(Path.Web.USER, UserController.updateUserProfile);

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

        get(Path.Web.Admin.ORDER + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_TIME + "/" + Param.ORDER_LIMIT,
                AdminOrderController.getAllOrdersByLastKey);

        get(Path.Web.Admin.ORDER + "/" + Param.ORDER_MAIL, AdminOrderController.getOrdersByMail);

        get(Path.Web.Admin.ORDER_PAID, AdminOrderController.getAllPaidOrders);

        get(Path.Web.Admin.ORDER_PAID + "/" + Param.ORDER_PAID_TIME + "/" + Param.ORDER_LIMIT,
                AdminOrderController.getAllPaidOrdersByLastKey);

        get(Path.Web.Admin.ORDER_PROCESSING, AdminOrderController.getAllProcessingOrders);

        get(Path.Web.Admin.ORDER_PROCESSING + "/" + Param.ORDER_PROCESSING_DATE + "/" + Param.ORDER_LIMIT,
                AdminOrderController.getAllProcessingOrdersByLastKey);

        get(Path.Web.Admin.ORDER_SHIPPED, AdminOrderController.getAllShippedOrders);

        get(Path.Web.Admin.ORDER_SHIPPED + "/" + Param.ORDER_SHIPPED_TIME + "/" + Param.ORDER_LIMIT,
                AdminOrderController.getAllShippedOrdersByLastKey);


        /**
         * order
         */

        get(Path.Web.Admin.ORDER + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_TIME,
                AdminOrderController.getOrderByMailAndTime);

        get(Path.Web.Admin.ORDER + "/" + Param.ORDER_MAIL,
                AdminOrderController.getOrdersByMail);

        /**
         * manipulate order lifecycle
         */

        post(Path.Web.Admin.ORDER_ACTIVE + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_TIME,
                AdminOrderController.activeOrder);

        post(Path.Web.Admin.ORDER_DEACTIVE + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_TIME,
                AdminOrderController.deActiveOrder);

        post(Path.Web.Admin.ORDER_PAY + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_TIME,
                AdminOrderController.payOrder);

        post(Path.Web.Admin.ORDER_DEPAY + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_TIME,
                AdminOrderController.dePayOrder);


        post(Path.Web.Admin.ORDER_SHIP + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_TIME,
                AdminOrderController.shipOrder);

        post(Path.Web.Admin.ORDER_DESHIP + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_TIME,
                AdminOrderController.deShipOrder);


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
