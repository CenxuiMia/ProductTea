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

            public static final String ORDER = "/admin/order/table";
            public static final String ORDER_ACTIVE = "/admin/order/table/active";
            public static final String ORDER_DEACTIVE = "/admin/order/table/deactive";
            public static final String ORDER_PAY = "/admin/order/table/pay";
            public static final String ORDER_DEPAY = "/admin/order/table/depay";
            public static final String ORDER_SHIP ="/admin/order/table/ship";
            public static final String ORDER_DESHIP ="/admin/order/table/deship";

            public static final String ORDER_PAID = "/admin/order/paid";
            public static final String ORDER_PROCESSING = "/admin/order/processing";
            public static final String ORDER_SHIPPED = "/admin/order/shipped";

        }
    }

}
