package com.cenxui.tea.app.aws.lambda.handlers;

import com.cenxui.tea.app.Application;

/**
 * todo mabe we don't need extend LambdaHandler
 */
public class UnAuthLambdaHandler extends LambdaHandler {
    @Override
    void defineRoutes() {
        Application.defineUnAuthResources();
    }
}
