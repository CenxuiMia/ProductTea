package com.cenxui.shop.util;

public class ApplicationError {
    public static String getTrace(StackTraceElement[] elements) {
        StringBuilder builder = new StringBuilder();

        for (StackTraceElement e : elements) {
            builder.append(e);
        }
        return builder.toString();
    }

}
