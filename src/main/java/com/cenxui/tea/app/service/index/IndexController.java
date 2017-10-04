package com.cenxui.tea.app.service.index;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class IndexController {

    public static ModelAndView serveHomePage(Request request, Response response) {
        response.body("index");
        return new ModelAndView(null, null);
    }

}
