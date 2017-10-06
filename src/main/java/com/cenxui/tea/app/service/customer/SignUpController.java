package com.cenxui.tea.app.service.customer;

import com.cenxui.tea.app.service.core.CoreController;
import spark.Request;
import spark.Response;
import spark.Route;

public class SignUpController extends CoreController {

    public static Route singUp = (Request request, Response response) -> {

        return "success";
    };


}
