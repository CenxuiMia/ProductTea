package com.cenxui.tea.app.services.util;

import lombok.Getter;

public class Path {

    public static class Web {
        public static final String INDEX = "/index";
        public static final String PRODUCT = "/product";
        public static final String ORDER = "/order";

        public static class Admin {
            public static final String USER = "/admin/user";
            public static final String PRODUCT = "/admin/product";
            public static final String ORDER = "/admin/order";

            public static final String USERS = "/admin/users";
            public static final String PRODUCTS = "/admin/products";
            public static final String ORDERS = "/admin/orders";

        }
    }

}
