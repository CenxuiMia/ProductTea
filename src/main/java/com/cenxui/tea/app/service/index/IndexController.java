package com.cenxui.tea.app.service.index;

import com.cenxui.tea.app.service.core.CoreController;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class IndexController extends CoreController {

    public static ModelAndView serveHomePage(Request request, Response response) {
        response.body("index");
        return new ModelAndView(null, null);
    }

}
