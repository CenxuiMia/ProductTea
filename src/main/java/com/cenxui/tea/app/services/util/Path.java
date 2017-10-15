package com.cenxui.tea.app.services.util;

import lombok.Getter;

public class Path {
    // The @Getter methods are needed in order to access
    // the variables from Velocity Templates
    public static class Web {
        @Getter
        public static final String INDEX = "/index/";
        @Getter public static final String SIGNIN = "/signin/";
        @Getter public static final String SIGNOUT = "/signout/";
        @Getter public static final String PRODUCTS = "/products";
        @Getter public static final String ONE_BOOK = "/books/:isbn/";
    }

}
