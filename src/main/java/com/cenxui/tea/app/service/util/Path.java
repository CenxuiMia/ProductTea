package com.cenxui.tea.app.service.util;

import lombok.Getter;

public class Path {
    // The @Getter methods are needed in order to access
    // the variables from Velocity Templates
    public static class Web {
        @Getter
        public static final String INDEX = "/index/";
        @Getter public static final String LOGIN = "/login/";
        @Getter public static final String LOGOUT = "/logout/";
        @Getter public static final String PRODUCTS = "/products";
        @Getter public static final String ONE_BOOK = "/books/:isbn/";
    }

}
