package com.cenxui.tea.app.services.util;

import lombok.Getter;

public class Path {

    public static class Web {
        public static final String INDEX = "/index";
        public static final String PRODUCT = "/product";
        public static final String ORDER = "/order";
        public static final String USER = "/user";

        public static class Admin {
            public static final String USER = "/admin/user";

            public static final String PRODUCT = "/admin/product";

            public static final String ORDER = "/admin/order";
            public static final String ORDERS_PAID = "/admin/orders/paid";
            public static final String ORDERS_PROCESSING = "/admin/orders/processing";
            public static final String ORDERS_SHIPPED = "/admin/orders/shipped";

        }
    }

}
