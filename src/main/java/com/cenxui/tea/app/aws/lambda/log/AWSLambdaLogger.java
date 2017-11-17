package com.cenxui.tea.app.aws.lambda.log;

import com.amazonaws.services.lambda.runtime.Context;

public class AWSLambdaLogger {
    public static void log(Context context, Exception e) {
        StringBuilder builder = new StringBuilder();

        builder.append(e.getMessage());
        builder.append("\n stack : ");
        for (StackTraceElement element : e.getStackTrace()) {
            builder.append(element.toString());
        }

        context.getLogger().log(builder.toString());
    }
}
