package com.cenxui.tea.app.service.index;

import com.cenxui.tea.app.service.core.Controller;
import com.cenxui.tea.app.service.core.ControllerImpl;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class IndexController extends ControllerImpl {

    public static ModelAndView serveHomePage(Request request, Response response) {
        response.body("index");
        return new ModelAndView(null, null);
    }

}
