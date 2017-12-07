package com.cenxui.tea.admin.app;

import static spark.Spark.*;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.cenxui.tea.admin.app.service.order.AdminOrderController;
import com.cenxui.tea.admin.app.service.product.AdminProductController;
import com.cenxui.tea.admin.app.service.product.AdminProductImageUploadController;
import com.cenxui.tea.admin.app.service.report.AdminCashController;
import com.cenxui.tea.admin.app.service.user.AdminUserController;
import com.cenxui.tea.admin.app.util.Param;
import com.cenxui.tea.admin.app.util.Path;

public class Application {

    public static void main(String[] args) {
        port(9000);

        before(((request, response) -> {
            /**
             * todo modify to https://tw.hwangying,com
             */
            response.header("Access-Control-Allow-Origin", "*");
        }));


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

        adminResources();

        exception(AmazonServiceException.class, (exception, request, response) -> {
            response.body("retry, error :" + exception.getMessage());
            response.status(500);
        });

        exception(AmazonClientException.class, (exception, request, response) -> {
            response.body(exception.getMessage());
            response.status(500);
        });

    }


    private static void adminResources() {
        /**
         * orders
         */

        get(Path.ORDER_ALL, AdminOrderController.getAllOrders);

        get(Path.ORDER_ALL + "/" +
                        Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME + "/" +
                        Param.ORDER_LIMIT,
                AdminOrderController.getAllOrdersByLastKey);

        get(Path.ORDER_TABLE + "/" +
                Param.ORDER_MAIL, AdminOrderController.getOrdersByMail);

        get(Path.ORDER_ALL_ACTIVE, AdminOrderController.getAllActiveOrders);

        get(Path.ORDER_ALL_ACTIVE + "/" +
                        Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME + "/" +
                        Param.ORDER_LIMIT,
                AdminOrderController.getAllActiveOrdersByLastKey);

        get(Path.ORDER_PAID, AdminOrderController.getAllPaidOrders);

        get(Path.ORDER_PAID +  "/" +
                        Param.ORDER_PAID_DATE  +"/" + Param.ORDER_PAID_TIME + "/" +
                        Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME + "/" +
                        Param.ORDER_LIMIT,
                AdminOrderController.getAllPaidOrdersByLastKey);

        get(Path.ORDER_PROCESSING, AdminOrderController.getAllProcessingOrders);

        get(Path.ORDER_PROCESSING + "/" +
                        Param.ORDER_PROCESSING_DATE + "/" + Param.ORDER_OWNER + "/" +
                        Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME + "/" +
                        Param.ORDER_LIMIT,
                AdminOrderController.getAllProcessingOrdersByLastKey);

        get(Path.ORDER_SHIPPED, AdminOrderController.getAllShippedOrders);

        get(Path.ORDER_SHIPPED + "/" +
                        Param.ORDER_SHIPPED_DATE + "/" + Param.ORDER_SHIPPED_TIME + "/" +
                        Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME + "/" +
                        Param.ORDER_LIMIT,
                AdminOrderController.getAllShippedOrdersByLastKey);


        /**
         * order
         */

        get(Path.ORDER_TABLE + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME,
                AdminOrderController.getOrderByMailAndTime);

        get(Path.ORDER_TABLE + "/" + Param.ORDER_MAIL,
                AdminOrderController.getOrdersByMail);

        /**
         * manipulate order lifecycle
         */

        post(Path.ORDER_TABLE_ACTIVE + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME,
                AdminOrderController.activeOrder);

        post(Path.ORDER_TABLE_DEACTIVE + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME,
                AdminOrderController.deActiveOrder);

        post(Path.ORDER_TABLE_PAY + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME,
                AdminOrderController.payOrder);

        post(Path.ORDER_TABLE_DEPAY + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME,
                AdminOrderController.dePayOrder);


        post(Path.ORDER_TABLE_SHIP + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME,
                AdminOrderController.shipOrder);

        post(Path.ORDER_TABLE_DESHIP + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME,
                AdminOrderController.deShipOrder);


        /**
         * add order
         */
        put(Path.ORDER_TABLE + "/" + Param.ORDER_MAIL + "/" + Param.ORDER_DATE_TIME,
                AdminOrderController.addOrder);


        /**
         * products
         */

        get(Path.PRODUCT_TABLE,  AdminProductController.getAllProducts);



        /**
         * product
         */

        get(Path.PRODUCT_TABLE + "/" + Param.PRODUCT_NAME + "/" + Param.PRODUCT_VERSION,
                AdminProductController.getProduct);

        /**
         * add product
         */
        put(Path.PRODUCT_TABLE, AdminProductController.addProduct);

        post(Path.PRODUCT_TABLE, AdminProductController.updateProduct);


        /**
         * product image
         */

        /**
         * add image
         */
        put(Path.PRODUCT_IMAGE, AdminProductImageUploadController.uploadProductImage);

        /**
         * user
         */

        get(Path.USER, AdminUserController.getAllUser);

        get(Path.USER + "/" + Param.ORDER_MAIL, AdminUserController.getUser);


        /**
         * report
         */

        get(Path.REPORT_CASH_ALL, AdminCashController.getAllCashReport);

        get(Path.REPORT_CASH_RANGE + "/" + Param.REPORT_CASH_FROM_DATE + "/" + Param.REPORT_CASH_TO_DATE,
                AdminCashController.getRangeCashReport);

        get(Path.REPORT_CASH_DAILY + "/" + Param.REPORT_CASH_DATE, AdminCashController.getDailyCashReport);

    }
}
