package com.cenxui.tea.app.services.index;

import com.cenxui.tea.app.services.CoreController;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class IndexController extends CoreController {

    public static ModelAndView serveHomePage(Request request, Response response) {
        response.body("index");
        return new ModelAndView(null, null);
    }

}
