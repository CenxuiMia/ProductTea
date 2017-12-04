package com.cenxui.tea.app;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;
import com.cenxui.tea.app.services.ControllerException;
import com.cenxui.tea.app.services.admin.order.AdminOrderController;
import com.cenxui.tea.app.services.admin.product.AdminProductController;
import com.cenxui.tea.app.services.admin.product.AdminProductImageUploadController;
import com.cenxui.tea.app.services.admin.user.AdminUserController;
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

    private static void adminResources() {
        /**
         * orders
         */

        get(Path.Admin.ORDER_ALL, AdminOrderController.getAllOrders);

        get(Path.Admin.ORDER_ALL + "/" +
                        Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME + "/" +
                        Param.ORDER_LIMIT,
                AdminOrderController.getAllOrdersByLastKey);

        get(Path.Admin.ORDER_TABLE + "/" +
                Param.ORDER_MAIL, AdminOrderController.getOrdersByMail);

        get(Path.Admin.ORDER_ALL_ACTIVE, AdminOrderController.getAllActiveOrders);

        get(Path.Admin.ORDER_TABLE_ACTIVE + "/" +
                        Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME + "/" +
                        Param.ORDER_LIMIT,
                AdminOrderController.getAllActiveOrdersByLastKey);

        get(Path.Admin.ORDER_PAID, AdminOrderController.getAllPaidOrders);

        get(Path.Admin.ORDER_PAID +  "/" +
                        Param.ORDER_PAID_DATE  +"/" + Param.ORDER_PAID_TIME + "/" +
                        Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME + "/" +
                        Param.ORDER_LIMIT,
                AdminOrderController.getAllPaidOrdersByLastKey);

        get(Path.Admin.ORDER_PROCESSING, AdminOrderController.getAllProcessingOrders);

        get(Path.Admin.ORDER_PROCESSING + "/" +
                        Param.ORDER_PROCESSING_DATE + "/" + Param.ORDER_OWNER + "/" +
                        Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME + "/" +
                        Param.ORDER_LIMIT,
                AdminOrderController.getAllProcessingOrdersByLastKey);

        get(Path.Admin.ORDER_SHIPPED, AdminOrderController.getAllShippedOrders);

        get(Path.Admin.ORDER_SHIPPED + "/" +
                        Param.ORDER_SHIPPED_DATE + "/" + Param.ORDER_SHIPPED_TIME + "/" +
                        Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME + "/" +
                        Param.ORDER_LIMIT,
                AdminOrderController.getAllShippedOrdersByLastKey);


        /**
         * order
         */

        get(Path.Admin.ORDER_TABLE + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME,
                AdminOrderController.getOrderByMailAndTime);

        get(Path.Admin.ORDER_TABLE + "/" + Param.ORDER_MAIL,
                AdminOrderController.getOrdersByMail);

        /**
         * manipulate order lifecycle
         */

        post(Path.Admin.ORDER_TABLE_ACTIVE + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME,
                AdminOrderController.activeOrder);

        post(Path.Admin.ORDER_TABLE_DEACTIVE + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME,
                AdminOrderController.deActiveOrder);

        post(Path.Admin.ORDER_TABLE_PAY + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME,
                AdminOrderController.payOrder);

        post(Path.Admin.ORDER_TABLE_DEPAY + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME,
                AdminOrderController.dePayOrder);


        post(Path.Admin.ORDER_TABLE_SHIP + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME,
                AdminOrderController.shipOrder);

        post(Path.Admin.ORDER_TABLE_DESHIP + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME,
                AdminOrderController.deShipOrder);


        /**
         * add order
         */
        put(Path.Admin.ORDER_TABLE + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME,
                AdminOrderController.addOrder);


        /**
         * products
         */

        get(Path.Admin.PRODUCT_TABLE,  AdminProductController.getAllProducts);



        /**
         * product
         */

        get(Path.Admin.PRODUCT_TABLE + "/" + Param.PRODUCT_NAME + "/" + Param.PRODUCT_VERSION,
                AdminProductController.getProduct);

        /**
         * add product
         */
        put(Path.Admin.PRODUCT_TABLE, AdminProductController.addProduct);

        post(Path.Admin.PRODUCT_TABLE, AdminProductController.updateProduct);


        /**
         * product image
         */

        /**
         * add image
         */
        put(Path.Admin.PRODUCT_IMAGE, AdminProductImageUploadController.uploadProductImage);

        /**
         * user
         */

        get(Path.Admin.USER, AdminUserController.getAllUser);

        get(Path.Admin.USER + "/" + Param.ORDER_MAIL, AdminUserController.getUser);

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
