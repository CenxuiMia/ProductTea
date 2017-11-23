package com.cenxui.tea.app.services.util;

import lombok.Getter;

public class Path {
    // The @Getter methods are needed in order to access
    // the variables from Velocity Templates
    public static class Web {
        @Getter public static final String INDEX = "/index";
        @Getter public static final String PRODUCT = "/product";
        @Getter public static final String ORDER = "/order";
        @Getter public static final String TEST= "/test";
        @Getter public static final String USER = "/user";
    }

}
